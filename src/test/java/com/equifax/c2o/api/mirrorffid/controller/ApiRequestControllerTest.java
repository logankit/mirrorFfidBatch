package com.equifax.c2o.api.mirrorffid.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.equifax.c2o.api.mirrorffid.service.ApiRequestService;

@WebMvcTest(ApiRequestController.class)
public class ApiRequestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApiRequestService apiRequestService;
    
    public ApiRequestControllerTest() {
        super();
    }

    @Test
    public void testGetQueuedRequests_Success() throws Exception {
        List<Long> mockRequestIds = Arrays.asList(1L, 2L, 3L);
        
        when(apiRequestService.getQueuedRequestIds()).thenReturn(mockRequestIds);

        mockMvc.perform(get("/queued-requests"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0]").value(1))
               .andExpect(jsonPath("$[1]").value(2))
               .andExpect(jsonPath("$[2]").value(3));
    }

    @Test
    public void testGetQueuedRequests_NoResults() throws Exception {
        when(apiRequestService.getQueuedRequestIds()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/queued-requests"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray())
               .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testHealthCheck() throws Exception {
        mockMvc.perform(get("/health"))
               .andExpect(status().isOk())
               .andExpect(content().string("Mirror FFID Handler Service is running"));
    }
    
    @Test
    public void testProcessRequests_Success() throws Exception {
        List<Long> mockRequestIds = Arrays.asList(1L, 2L, 3L);
        when(apiRequestService.getQueuedRequestIds()).thenReturn(mockRequestIds);
        when(apiRequestService.updateRequestStatusTo304(mockRequestIds)).thenReturn(3);
        
        mockMvc.perform(get("/process-requests"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value("success"))
               .andExpect(jsonPath("$.message").value("Successfully processed requests"))
               .andExpect(jsonPath("$.requestsFound").value(3))
               .andExpect(jsonPath("$.requestsProcessed").value(3))
               .andExpect(jsonPath("$.requestIds[0]").value(1))
               .andExpect(jsonPath("$.requestIds[1]").value(2))
               .andExpect(jsonPath("$.requestIds[2]").value(3));
    }
    
    @Test
    public void testProcessRequests_NoRequests() throws Exception {
        when(apiRequestService.getQueuedRequestIds()).thenReturn(Collections.emptyList());
        
        mockMvc.perform(get("/process-requests"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value("success"))
               .andExpect(jsonPath("$.message").value("No requests found for processing"))
               .andExpect(jsonPath("$.requestsFound").value(0))
               .andExpect(jsonPath("$.requestsProcessed").value(0));
    }
    
    @Test
    public void testTestEndpoint() throws Exception {
        mockMvc.perform(get("/test"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value("success"))
               .andExpect(jsonPath("$.message").value("Test endpoint is working"))
               .andExpect(jsonPath("$.timestamp").isNumber());
    }
}
