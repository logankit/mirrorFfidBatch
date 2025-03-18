package com.equifax.c2o.api.mirrorffid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableScheduling
public class MirrorFfidHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MirrorFfidHandlerApplication.class, args);
    }
    
    /**
     * Configure ObjectMapper bean for JSON serialization/deserialization.
     * This bean is used in QueueMessageService for serializing payload to JSON.
     * 
     * @return Configured ObjectMapper instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
