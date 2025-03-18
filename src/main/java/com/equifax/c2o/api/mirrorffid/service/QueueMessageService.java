package com.equifax.c2o.api.mirrorffid.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equifax.c2o.api.mirrorffid.dto.QueueMessagePayload;
import com.equifax.c2o.api.mirrorffid.entity.LogMessage;
import com.equifax.c2o.api.mirrorffid.entity.QueueMessage;
import com.equifax.c2o.api.mirrorffid.util.CommonConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * Service responsible for handling queue message operations.
 * This includes creating queue messages for processed requests (status 304).
 */
@Service
public class QueueMessageService {

    private static final Logger logger = LoggerFactory.getLogger(QueueMessageService.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    private final ObjectMapper objectMapper;
    
    public QueueMessageService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    /**
     * Creates queue message entries for a list of request IDs.
     * Each queue message will have a unique UUID, and will reference
     * the corresponding request ID in attribute1.
     * 
     * @param requestIds List of request IDs to create queue messages for
     * @return The number of queue messages created
     */
    @Transactional
    public int createQueueMessagesForRequests(List<Long> requestIds) {
        if (requestIds == null || requestIds.isEmpty()) {
            logger.info("No request IDs provided for queue message creation");
            return 0;
        }
        
        List<QueueMessage> queueMessages = new ArrayList<>();
        List<LogMessage> logMessages = new ArrayList<>();
        int successCount = 0;
        
        try {
            for (Long requestId : requestIds) {
                // Create a unique message ID (UUID)
                String messageId = UUID.randomUUID().toString();
                
                // Create the payload with request ID
                QueueMessagePayload payload = new QueueMessagePayload();
                payload.setMessageId(messageId);
                payload.setRequestId(requestId);
                payload.setBatchId(CommonConstants.Queue.MIRROR_FFID_BATCH_ID);
                
                // Convert payload to JSON
                String jsonPayload = objectMapper.writeValueAsString(payload);
                
                // Create queue message entity
                QueueMessage queueMessage = new QueueMessage();
                queueMessage.setMessageId(messageId);
                queueMessage.setBatchId(CommonConstants.Queue.MIRROR_FFID_BATCH_ID);
                queueMessage.setMessageStatus(CommonConstants.Status.QUEUE_MESSAGE_STATUS);
                queueMessage.setQueueMessage(jsonPayload);
                queueMessage.setProducer(CommonConstants.Queue.PRODUCER);
                queueMessage.setDestinationQueue(CommonConstants.Queue.DESTINATION);
                queueMessage.setCreatedOn(LocalDateTime.now());
                queueMessage.setCreatedBy(CommonConstants.Queue.USER);
                queueMessage.setUpdatedOn(LocalDateTime.now());
                queueMessage.setUpdatedBy(CommonConstants.Queue.USER);
                queueMessage.setMessageLogLevel(CommonConstants.Queue.LOG_LEVEL);
                queueMessage.setAttribute1(requestId.toString());
                
                queueMessages.add(queueMessage);
                
                // Create corresponding log message
                LogMessage logMessage = createLogMessage(messageId, requestId);
                logMessages.add(logMessage);
                
                successCount++;
            }
            
            // Persist all queue messages
            for (QueueMessage message : queueMessages) {
                entityManager.persist(message);
            }
            
            // Persist all log messages
            for (LogMessage logMessage : logMessages) {
                entityManager.persist(logMessage);
            }
            
            logger.info("Created {} queue message entries with corresponding log entries", successCount);
            return successCount;
        } catch (Exception e) {
            logger.error("Error creating queue messages", e);
            throw new RuntimeException("Error creating queue messages: " + e.getMessage(), e);
        }
    }
    
    /**
     * Creates a log message entity for a queue message.
     * 
     * @param messageId The UUID of the queue message
     * @param requestId The request ID associated with the queue message
     * @return LogMessage entity ready to be persisted
     */
    private LogMessage createLogMessage(String messageId, Long requestId) {
        LogMessage logMessage = new LogMessage();
        
        // Generate log message ID from sequence
        Query seqQuery = entityManager.createNativeQuery("SELECT " + CommonConstants.Log.LOG_MESSAGE_SEQ + ".NEXTVAL FROM DUAL");
        Number logMessageId = (Number) seqQuery.getSingleResult();
        
        // Populate log message fields
        logMessage.setLogMessageId(logMessageId.longValue());
        logMessage.setMessageId(messageId);
        logMessage.setBatchId(CommonConstants.Queue.MIRROR_FFID_BATCH_ID);
        logMessage.setComponentName(CommonConstants.Queue.PRODUCER);
        logMessage.setLogMessage(String.format(CommonConstants.Log.MESSAGE_FORMAT, messageId, requestId));
        logMessage.setCreatedOn(LocalDateTime.now());
        logMessage.setCreatedBy(CommonConstants.Queue.USER);
        logMessage.setLastUpdatedOn(LocalDateTime.now());
        logMessage.setLastUpdatedBy(CommonConstants.Queue.USER);
        logMessage.setMessageType(CommonConstants.Log.MESSAGE_TYPE);
        logMessage.setAttribute1(requestId.toString());
        
        return logMessage;
    }
}
