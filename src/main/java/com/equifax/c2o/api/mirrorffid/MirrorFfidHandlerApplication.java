package com.equifax.c2o.api.mirrorffid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MirrorFfidHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MirrorFfidHandlerApplication.class, args);
    }
}
