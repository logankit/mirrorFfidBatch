package com.equifax.c2o.api.mirrorffid.service;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.equifax.c2o.api.mirrorffid.util.CommonConstants;

@Service
public class ApiRequestService {

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestService.class);

    @PersistenceContext
    private EntityManager entityManager;
    
    private final QueueMessageService queueMessageService;
    
    public ApiRequestService(QueueMessageService queueMessageService) {
        super();
        this.queueMessageService = queueMessageService;
    }

    @SuppressWarnings("unchecked")
    public List<Long> getQueuedRequestIds() {
        try {
            String sql = "SELECT car.request_id " +
                        "FROM C2O_API_REQUEST car " +
                        "WHERE car.REQUEST_STATUS = " + CommonConstants.Status.QUEUED + " " +
                        "AND EXISTS ( " +
                        "    SELECT 1 " +
                        "    FROM C2O_CONTRACT cc " +
                        "    WHERE cc.CONTRACT_ID = car.BASE_CONTRACT_ID " +
                        "    AND cc.LOCKED_BY IS NULL " +
                        "    AND cc.VERSION_NUMBER = ( " +
                        "        SELECT MAX(VERSION_NUMBER) " +
                        "        FROM C2O_CONTRACT " +
                        "        WHERE ROOT_CONTRACT_ID = cc.ROOT_CONTRACT_ID " +
                        "    ) " +
                        ") " +
                        "AND car.CREATED_DATE = ( " +
                        "    SELECT MIN(CREATED_DATE) " +
                        "    FROM C2O_API_REQUEST " +
                        "    WHERE BASE_CONTRACT_ID = car.BASE_CONTRACT_ID " +
                        "    AND REQUEST_STATUS = " + CommonConstants.Status.QUEUED + " " +
                        ")";
            
            logger.debug("Executing SQL query: {}", sql);
            
            Query query = entityManager.createNativeQuery(sql);
            List<?> results = query.getResultList();
            
            List<Long> requestIds = new ArrayList<>();
            for (Object result : results) {
                if (result instanceof BigDecimal) {
                    requestIds.add(((BigDecimal) result).longValue());
                } else if (result instanceof Number) {
                    requestIds.add(((Number) result).longValue());
                } else if (result instanceof String) {
                    requestIds.add(Long.parseLong((String) result));
                } else if (result != null) {
                    requestIds.add(Long.parseLong(result.toString()));
                }
            }
            
            logger.info("Found {} queued requests", requestIds.size());
            return requestIds;
        } catch (Exception e) {
            logger.error("Error retrieving queued request IDs", e);
            throw new RuntimeException("Error retrieving queued request IDs: " + e.getMessage(), e);
        }
    }
    
    /**
     * Updates the status of specified API requests to processed status.
     * This is typically used for requests that need to be marked as processed
     * in a specific way in the mirrorFFID system.
     *
     * For each request updated to status 304, an entry is made in the
     * C2O_MP_QUEUE_MESSAGE table with:
     * - batch id 666666666
     * - message status 231
     * - producer "ContractAPIController" 
     * - destination "t07_BusinessValidationSQS"
     * - unique message id generated as UUID
     *
     * @param requestIds List of request IDs to update
     * @return The number of records updated
     */
    @Transactional
    public int updateRequestStatusTo304(List<Long> requestIds) {
        if (requestIds == null || requestIds.isEmpty()) {
            logger.info("No request IDs provided for status update to {}", CommonConstants.Status.PROCESSED);
            return 0;
        }
        
        try {
            // First update the status of the requests
            String sql = "UPDATE C2O_API_REQUEST " +
                        "SET REQUEST_STATUS = " + CommonConstants.Status.PROCESSED + ", " +
                        "LAST_UPDATED_DATE = CURRENT_TIMESTAMP " +
                        "WHERE REQUEST_ID IN (:requestIds)";
            
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("requestIds", requestIds);
            
            int updatedCount = query.executeUpdate();
            logger.info("Updated {} requests to status {}", updatedCount, CommonConstants.Status.PROCESSED);
            
            // If some requests were updated successfully, create queue message entries for them
            if (updatedCount > 0) {
                // Create queue messages for all successfully updated requests
                int queueMessagesCreated = queueMessageService.createQueueMessagesForRequests(requestIds);
                logger.info("Created {} queue messages for {} updated requests", 
                           queueMessagesCreated, updatedCount);
                
                if (queueMessagesCreated < updatedCount) {
                    logger.warn("Not all queue messages were created. Expected: {}, Actual: {}", 
                               updatedCount, queueMessagesCreated);
                }
            }
            
            return updatedCount;
        } catch (Exception e) {
            logger.error("Error updating request status to {}", CommonConstants.Status.PROCESSED, e);
            throw new RuntimeException("Error updating request status to " + CommonConstants.Status.PROCESSED + ": " + e.getMessage(), e);
        }
    }
}
