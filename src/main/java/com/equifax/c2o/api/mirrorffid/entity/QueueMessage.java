package com.equifax.c2o.api.mirrorffid.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

/**
 * Entity class representing a message in the C2O_MP_QUEUE_MESSAGE table.
 */
@Entity
@Table(name = "C2O_MP_QUEUE_MESSAGE")
public class QueueMessage {

    @Id
    @Column(name = "MESSAGE_ID")
    private String messageId;
    
    @Column(name = "BATCH_ID", nullable = false)
    private Long batchId;
    
    @Column(name = "MESSAGE_STATUS")
    private Integer messageStatus;
    
    @Lob
    @Column(name = "QUEUE_MESSAGE")
    private String queueMessage;
    
    @Column(name = "PRODUCER")
    private String producer;
    
    @Column(name = "DESTINATION_QUEUE")
    private String destinationQueue;
    
    @Column(name = "REPLAYED_ON")
    private LocalDateTime replayedOn;
    
    @Column(name = "REPLAYED_BY")
    private String replayedBy;
    
    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;
    
    @Column(name = "CREATED_BY")
    private String createdBy;
    
    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;
    
    @Column(name = "UPDATED_BY")
    private String updatedBy;
    
    @Column(name = "MESSAGE_LOG_LEVEL", nullable = false)
    private String messageLogLevel;
    
    @Column(name = "ATTRIBUTE1")
    private String attribute1;

    // Getters and setters
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

    public Integer getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Integer messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getQueueMessage() {
        return queueMessage;
    }

    public void setQueueMessage(String queueMessage) {
        this.queueMessage = queueMessage;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getDestinationQueue() {
        return destinationQueue;
    }

    public void setDestinationQueue(String destinationQueue) {
        this.destinationQueue = destinationQueue;
    }

    public LocalDateTime getReplayedOn() {
        return replayedOn;
    }

    public void setReplayedOn(LocalDateTime replayedOn) {
        this.replayedOn = replayedOn;
    }

    public String getReplayedBy() {
        return replayedBy;
    }

    public void setReplayedBy(String replayedBy) {
        this.replayedBy = replayedBy;
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

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getMessageLogLevel() {
        return messageLogLevel;
    }

    public void setMessageLogLevel(String messageLogLevel) {
        this.messageLogLevel = messageLogLevel;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }
}
