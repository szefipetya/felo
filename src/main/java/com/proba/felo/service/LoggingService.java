package com.proba.felo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    @Qualifier("jsonMapper")
    @Autowired
    ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(LoggingService.class);
    
    public void logApiCall(ResponseEntity<?> responseEntity, String URI, HttpMethod method) {
        logger.info("Api call completed. Details: " + String.format("\n" +
                        "RequestUrl: %s\nHttpMethod: %s\nResponseStatusCode: %s\nResponseBody: %s\n" +
                        "--------------------------------------------",
                URI, method, responseEntity.getStatusCodeValue(), responseEntity.getBody()));
    }
}
