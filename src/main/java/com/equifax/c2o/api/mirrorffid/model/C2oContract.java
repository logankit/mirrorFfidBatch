package com.equifax.c2o.api.mirrorffid.model;

import java.sql.Date;
import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "C2O_CONTRACT")
public class C2oContract {
    
    @Id
    @Column(name = "CONTRACT_ID")
    private Long contractId;
    
    @Column(name = "INVOICE_DELIVERY_METHOD")
    private String invoiceDeliveryMethod;
    
    @Column(name = "BILLING_INTERVAL")
    private String billingInterval;
    
    @Column(name = "PAYMENT_TERMS")
    private String paymentTerms;
    
    @Column(name = "PAYMENT_TYPE")
    private String paymentType;
    
    @Column(name = "PO_NUMBER")
    private String poNumber;
    
    @Column(name = "PO_AMOUNT")
    private Long poAmount;
    
    @Column(name = "PO_STARTDATE")
    private Date poStartDate;
    
    @Column(name = "PO_ENDDATE")
    private Date poEndDate;
    
    @Column(name = "CONTRACT_LANGUAGE")
    private String contractLanguage;
    
    @Column(name = "CONTRACT_EFFECTIVEDATE")
    private Date contractEffectiveDate;
    
    @Column(name = "CONTRACT_EXPIRATIONDATE")
    private Date contractExpirationDate;
    
    @Column(name = "AUTO_RENEWALDATE")
    private Date autoRenewalDate;
    
    @Column(name = "CONTRIBUTOR_TYPE")
    private String contributorType;
    
    @Column(name = "EQUIFAX_LEGAL_NAME")
    private String equifaxLegalName;
    
    @Column(name = "EQUIFAX_COMPANY_NUMBER")
    private String equifaxCompanyNumber;
    
    @Column(name = "BUSINESS_UNIT")
    private String businessUnit;
    
    @Column(name = "DELIVERY_METHOD")
    private String deliveryMethod;
    
    @Column(name = "CREATEDBY")
    private String createdBy;
    
    @Column(name = "CREATEDDATE")
    private Timestamp createdDate;
    
    @Column(name = "UPDATEDBY")
    private String updatedBy;
    
    @Column(name = "UPDATEDDATE")
    private Timestamp updatedDate;
    
    @Column(name = "ROOT_CONTRACT_ID")
    private Long rootContractId;
    
    @Column(name = "VERSION_NUMBER")
    private Long versionNumber;
    
    @Column(name = "PARENT_CONTRACT_ID")
    private Long parentContractId;
    
    @Column(name = "CONTRACT_NUMBER")
    private String contractNumber;
    
    @Column(name = "ACCOUNT_ID")
    private Long accountId;
    
    @Column(name = "LOCKED_BY")
    private String lockedBy;
    
    @Column(name = "LOCKED_DATE")
    private Date lockedDate;
    
    // Add other fields as needed
}
