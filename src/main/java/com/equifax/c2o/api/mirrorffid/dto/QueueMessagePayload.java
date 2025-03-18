package com.equifax.c2o.api.mirrorffid.dto;

/**
 * DTO representing the payload of a queue message.
 * This is serialized to JSON and stored in the QUEUE_MESSAGE field.
 */
public class QueueMessagePayload {

    private String messageId;
    private Long requestId;
    private Long batchId;
    private Long contractId;
    private Long orderId;
    private String requestType;
    private String user;
    
    /**
     * Default constructor
     */
    public QueueMessagePayload() {
        // Default constructor required for Jackson
    }
    
    /**
     * Constructor with essential fields
     * 
     * @param messageId The unique message ID (UUID)
     * @param requestId The request ID associated with this message
     * @param batchId The batch ID for processing
     */
    public QueueMessagePayload(String messageId, Long requestId, Long batchId) {
        this.messageId = messageId;
        this.requestId = requestId;
        this.batchId = batchId;
    }

    // Getters and Setters
    
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
