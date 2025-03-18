package com.equifax.c2o.api.mirrorffid.util;

/**
 * Common constants used throughout the application.
 */
public final class CommonConstants {
    
    // Private constructor to prevent instantiation
    private CommonConstants() {
        // Utility class should not be instantiated
    }
    
    /**
     * API Request Status constants
     */
    public static final class Status {
        /**
         * Status code 304 - Used to mark requests as processed in the mirrorFFID system.
         */
        public static final String PROCESSED = "304";
        
        /**
         * Status code 318 - Used to mark requests as queued/pending.
         */
        public static final String QUEUED = "318";
        
        /**
         * Status code 231 - Used for queue message status.
         */
        public static final int QUEUE_MESSAGE_STATUS = 231;
    }
    
    /**
     * Message Queue constants
     */
    public static final class Queue {
        /**
         * Batch ID for mirrorFFID processing
         */
        public static final long MIRROR_FFID_BATCH_ID = 666666666L;
        
        /**
         * Producer name for mirrorFFID queue messages
         */
        public static final String PRODUCER = "C2O_MirrorFFID";
        
        /**
         * Destination queue name for business validation
         */
        public static final String DESTINATION = "t07_BusinessValidationSQS";
        
        /**
         * Default creator/updater name for queue messages
         */
        public static final String USER = "C2O_MP";
        
        /**
         * Default log level for queue messages
         */
        public static final String LOG_LEVEL = "INFO";
    }
    
    /**
     * Nested class containing constants related to log messages.
     */
    public static final class Log {
        /**
         * Message type for queue-related log messages (INFO)
         */
        public static final String MESSAGE_TYPE = "INFO";
        
        /**
         * Log message format for queue messages
         */
        public static final String MESSAGE_FORMAT = "Queue message created with ID: %s for request ID: %s";
        
        /**
         * Name of the sequence for log message IDs
         */
        public static final String LOG_MESSAGE_SEQ = "C2O_MP_LOG_SEQ";
    }
}
