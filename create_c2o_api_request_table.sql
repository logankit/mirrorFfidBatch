CREATE TABLE C2O_API_REQUEST (
    REQUEST_ID NUMBER PRIMARY KEY,
    SOURCE_SYSTEM_ID VARCHAR2(50),
    CORELATION_ID VARCHAR2(50),
    REQUEST_PAYLOAD CLOB,
    REQUEST_STATUS VARCHAR2(50),
    CONTRACT_ID NUMBER,
    CONTRACT_TYPE VARCHAR2(50),
    LE_EFX_ID NUMBER,
    BILLING_SOURCE VARCHAR2(50),
    BUSINESS_UNIT VARCHAR2(50),
    CREATED_DATE TIMESTAMP,
    LAST_UPDATED_DATE TIMESTAMP,
    RESPONSE_PAYLOAD CLOB,
    HEADER_PAYLOAD CLOB,
    RESPONSE_SENT VARCHAR2(1),
    REQUEST_PAYLOAD2 CLOB,
    REQUEST_TYPE VARCHAR2(50),
    BASE_CONTRACT_ID NUMBER,
    RESPONSE_SENT_ON TIMESTAMP,
    REDRIVE_FLAG VARCHAR2(1),
    CASE_ID NUMBER,
    ORDER_ID NUMBER,
    SOURCE_ACK VARCHAR2(50),
    SOURCE_ACK_TIME TIMESTAMP,
    SOURCE_ACK_ID VARCHAR2(50),
    EVENT_ID NUMBER,
    CLOSED VARCHAR2(1),
    CLOSED_ON TIMESTAMP,
    QUEUED_PROCESSING_FLAG VARCHAR2(1),
    SOURCE_REQUEST_ID VARCHAR2(50),
    SOURCE_FFID VARCHAR2(50)
);
