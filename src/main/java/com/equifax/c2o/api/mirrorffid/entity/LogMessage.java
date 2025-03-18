package com.equifax.c2o.api.mirrorffid.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a record in the C2O_MP_LOG_MESSAGE table.
 * This table stores log messages related to queue processing.
 */
@Entity
@Table(name = "C2O_MP_LOG_MESSAGE")
public class LogMessage {
    
    @Id
    @Column(name = "LOG_MESSAGE_ID")
    private Long logMessageId;
    
    @Column(name = "MESSAGE_ID", nullable = false, length = 100)
    private String messageId;
    
    @Column(name = "BATCH_ID")
    private Long batchId;
    
    @Column(name = "COMPONENT_NM", length = 50)
    private String componentName;
    
    @Column(name = "LOG_MESSAGE")
    private String logMessage;
    
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;
    
    @Column(name = "CREATED_BY", length = 50)
    private String createdBy;
    
    @Column(name = "LAST_UPDATED_ON")
    private LocalDateTime lastUpdatedOn;
    
    @Column(name = "LAST_UPDATED_BY", length = 50)
    private String lastUpdatedBy;
    
    @Column(name = "MESSAGE_TYPE", length = 20)
    private String messageType;
    
    @Column(name = "ATTRIBUTE1", length = 100)
    private String attribute1;

    // Getters and Setters
    
    public Long getLogMessageId() {
        return logMessageId;
    }

    public void setLogMessageId(Long logMessageId) {
        this.logMessageId = logMessageId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }
}
