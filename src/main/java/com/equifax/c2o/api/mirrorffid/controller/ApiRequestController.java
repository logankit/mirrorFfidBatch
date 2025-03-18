package com.equifax.c2o.api.mirrorffid.controller;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equifax.c2o.api.mirrorffid.service.ApiRequestService;
import com.equifax.c2o.api.mirrorffid.util.CommonConstants;

@RestController
@RequestMapping("")
public class ApiRequestController {

    private static final Logger logger = LoggerFactory.getLogger(ApiRequestController.class);
    private final ApiRequestService apiRequestService;

    @Autowired
    public ApiRequestController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    @GetMapping("/queued-requests")
    public ResponseEntity<List<Long>> getQueuedRequests() {
        List<Long> queuedRequestIds = apiRequestService.getQueuedRequestIds();
        return ResponseEntity.ok(queuedRequestIds);
    }
    
    /**
     * Endpoint to manually trigger the mirror FFID status update process.
     * This performs the same operation as the scheduled batch job.
     * 
     * 1. Retrieves all queued request IDs that meet the criteria
     * 2. Updates their status to 304 (Processed)
     * 3. Creates queue messages and log messages for each request
     * 
     * @return Response with details about the processed requests
     */
    @GetMapping("/process-requests")
    public ResponseEntity<Map<String, Object>> processRequests() {
        logger.info("Manual trigger to process mirror FFID requests with status {}", CommonConstants.Status.QUEUED);
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Get all queued request IDs
            logger.debug("Fetching queued request IDs");
            List<Long> requestIds = apiRequestService.getQueuedRequestIds();
            logger.debug("Retrieved {} queued request IDs", requestIds.size());
            
            if (requestIds.isEmpty()) {
                logger.info("No requests found for processing");
                response.put("status", "success");
                response.put("message", "No requests found for processing");
                response.put("requestsFound", 0);
                response.put("requestsProcessed", 0);
                return ResponseEntity.ok(response);
            }
            
            logger.info("Found {} requests to update to status {}", requestIds.size(), CommonConstants.Status.PROCESSED);
            
            // Update the status of all retrieved requests to 304
            logger.debug("Updating status of {} requests to {}", requestIds.size(), CommonConstants.Status.PROCESSED);
            int updatedCount = apiRequestService.updateRequestStatusTo304(requestIds);
            
            logger.info("Successfully processed {} out of {} requests", updatedCount, requestIds.size());
            
            response.put("status", "success");
            response.put("message", "Successfully processed requests");
            response.put("requestsFound", requestIds.size());
            response.put("requestsProcessed", updatedCount);
            response.put("requestIds", requestIds);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error processing mirror FFID requests: {}", e.getMessage(), e);
            
            // Capture more details about the exception
            response.put("status", "error");
            response.put("message", "Error processing requests: " + e.getMessage());
            response.put("exceptionType", e.getClass().getName());
            
            // Add stack trace information
            String stackTrace = "";
            for (StackTraceElement element : e.getStackTrace()) {
                stackTrace += element.toString() + "\n";
                if (stackTrace.length() > 1000) {
                    stackTrace += "... (truncated)";
                    break;
                }
            }
            response.put("stackTrace", stackTrace);
            
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Mirror FFID Handler Service is running");
    }
    
    /**
     * Simple test endpoint to check basic functionality without DB operations
     */
    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", System.currentTimeMillis());
        response.put("message", "Test endpoint is working");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }
}
