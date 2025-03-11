package com.equifax.c2o.api.mirrorffid.model;

import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "C2O_API_REQUEST")
public class C2oApiRequest {
    
    @Id
    @Column(name = "REQUEST_ID")
    private Long requestId;
    
    @Column(name = "SOURCE_SYSTEM_ID")
    private String sourceSystemId;
    
    @Column(name = "CORELATION_ID")
    private String corelationId;
    
    @Lob
    @Column(name = "REQUEST_PAYLOAD")
    private String requestPayload;
    
    @Column(name = "REQUEST_STATUS")
    private String requestStatus;
    
    @Column(name = "CONTRACT_ID")
    private Long contractId;
    
    @Column(name = "CONTRACT_TYPE")
    private String contractType;
    
    @Column(name = "LE_EFX_ID")
    private Long leEfxId;
    
    @Column(name = "BILLING_SOURCE")
    private String billingSource;
    
    @Column(name = "BUSINESS_UNIT")
    private String businessUnit;
    
    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;
    
    @Column(name = "LAST_UPDATED_DATE")
    private Timestamp lastUpdatedDate;
    
    @Lob
    @Column(name = "RESPONSE_PAYLOAD")
    private String responsePayload;
    
    @Lob
    @Column(name = "HEADER_PAYLOAD")
    private String headerPayload;
    
    @Column(name = "RESPONSE_SENT")
    private String responseSent;
    
    @Lob
    @Column(name = "REQUEST_PAYLOAD2")
    private String requestPayload2;
    
    @Column(name = "REQUEST_TYPE")
    private String requestType;
    
    @Column(name = "BASE_CONTRACT_ID")
    private Long baseContractId;
    
    @Column(name = "RESPONSE_SENT_ON")
    private Timestamp responseSentOn;
    
    @Column(name = "REDRIVE_FLAG")
    private String redriveFlag;
    
    @Column(name = "CASE_ID")
    private Long caseId;
    
    @Column(name = "ORDER_ID")
    private Long orderId;
    
    @Column(name = "SOURCE_ACK")
    private String sourceAck;
    
    @Column(name = "SOURCE_ACK_TIME")
    private Timestamp sourceAckTime;
    
    @Column(name = "SOURCE_ACK_ID")
    private String sourceAckId;
    
    @Column(name = "EVENT_ID")
    private Long eventId;
    
    @Column(name = "CLOSED")
    private String closed;
    
    @Column(name = "CLOSED_ON")
    private Timestamp closedOn;
    
    @Column(name = "QUEUED_PROCESSING_FLAG")
    private String queuedProcessingFlag;
    
    @Column(name = "SOURCE_REQUEST_ID")
    private String sourceRequestId;
    
    @Column(name = "SOURCE_FFID")
    private String sourceFfid;
}
