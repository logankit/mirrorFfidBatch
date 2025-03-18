package com.equifax.c2o.api.mirrorffid.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.equifax.c2o.api.mirrorffid.service.ApiRequestService;
import com.equifax.c2o.api.mirrorffid.util.CommonConstants;

/**
 * Batch job that runs every 30 minutes to update mirrorFFID request status
 * from query status to '304'.
 */
@Component
public class MirrorFfidStatusUpdateBatch {

    private static final Logger logger = LoggerFactory.getLogger(MirrorFfidStatusUpdateBatch.class);
    
    private final ApiRequestService apiRequestService;
    
    @Autowired
    public MirrorFfidStatusUpdateBatch(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }
    
    /**
     * Scheduled job that runs every 30 minutes.
     * Gets all queued requests from the service and updates their status to '304'.
     * 
     * The cron expression "0 0/30 * * * ?" means:
     * - 0 seconds
     * - Every 30 minutes (0, 30)
     * - Every hour
     * - Every day of month
     * - Every month
     * - Every day of week
     */
    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateRequestStatus() {
        logger.info("Starting scheduled job to update request status to {}", CommonConstants.Status.PROCESSED);
        
        try {
            // Get all queued request IDs from the service
            List<Long> requestIds = apiRequestService.getQueuedRequestIds();
            
            if (requestIds.isEmpty()) {
                logger.info("No requests found for status update");
                return;
            }
            
            logger.info("Found {} requests to update to status {}", requestIds.size(), CommonConstants.Status.PROCESSED);
            
            // Update the status of all retrieved requests to 304
            int updatedCount = apiRequestService.updateRequestStatusTo304(requestIds);
            
            logger.info("Successfully updated {} out of {} requests to status {}", 
                    updatedCount, requestIds.size(), CommonConstants.Status.PROCESSED);
            
            if (updatedCount < requestIds.size()) {
                logger.warn("Not all requests were updated. Expected: {}, Actual: {}", 
                        requestIds.size(), updatedCount);
            }
        } catch (Exception e) {
            logger.error("Error in scheduled request status update job", e);
        }
        
        logger.info("Completed scheduled job to update request status to {}", CommonConstants.Status.PROCESSED);
    }
}
