package com.equifax.c2o.api.mirrorffid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equifax.c2o.api.mirrorffid.service.ApiRequestService;

@RestController
@RequestMapping("/api/mirror-ffid")
public class ApiRequestController {

    private final ApiRequestService apiRequestService;

    @Autowired
    public ApiRequestController(ApiRequestService apiRequestService) {
        this.apiRequestService = apiRequestService;
    }

    @GetMapping("/queued-requests")
    public ResponseEntity<List<Long>> getQueuedRequests() {
        List<Long> queuedRequestIds = apiRequestService.getQueuedRequestIds();
        return ResponseEntity.ok(queuedRequestIds);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Mirror FFID Handler Service is running");
    }
}
