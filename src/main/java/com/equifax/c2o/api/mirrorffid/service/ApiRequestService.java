package com.equifax.c2o.api.mirrorffid.service;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class ApiRequestService {

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestService.class);

    @PersistenceContext
    private EntityManager entityManager;
    
    public ApiRequestService() {
        super();
    }

    @SuppressWarnings("unchecked")
    public List<Long> getQueuedRequestIds() {
        try {
            String sql = "SELECT car.request_id " +
                        "FROM C2O_API_REQUEST car " +
                        "WHERE car.REQUEST_STATUS = '318' " +
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
                        "    AND REQUEST_STATUS = '318' " +
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
}
