package com.app.LaborNakuriBackend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.Enumeration;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Generate a unique request ID for tracking
        String requestId = UUID.randomUUID().toString();

        // Add the request ID to the response headers for tracking
        response.setHeader("X-Request-ID", requestId);

        // Store the request ID in the request for later use
        request.setAttribute("requestId", requestId);

        // Store the start time in the request for later use
        request.setAttribute("startTime", Instant.now());

        return true; // Continue with request processing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Retrieve the request ID and start time from the request
        String requestId = (String) request.getAttribute("requestId");
        Instant startTime = (Instant) request.getAttribute("startTime");

        if (requestId == null || startTime == null) {
            // Log a warning if these attributes are missing
            System.out.println("Warning: Missing request tracking attributes.");
            return;
        }
        System.out.println("startTime"+startTime);

        // Calculate the processing time
        Instant endTime = Instant.now();
        long processingTime = endTime.toEpochMilli() - startTime.toEpochMilli();
        System.out.println("endTime"+endTime);

        // Build the log buffer
        StringBuilder logBuffer = new StringBuilder();
        logBuffer.append("[Request ID: ").append(requestId).append("] Request received:\n");
        logBuffer.append("[Request ID: ").append(requestId).append("] Method: ").append(request.getMethod()).append("\n");
        logBuffer.append("[Request ID: ").append(requestId).append("] URI: ").append(request.getRequestURI()).append("\n");

        // Add headers to the log buffer
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logBuffer.append("[Request ID: ").append(requestId).append("] ").append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        }

        // Append response status and processing time
        logBuffer.append("[Request ID: ").append(requestId).append("] Response Status: ").append(response.getStatus()).append("\n");
        logBuffer.append("[Request ID: ").append(requestId).append("] Processing Time: ").append(processingTime).append(" ms\n");

        // Output the log
        System.out.println(logBuffer.toString());
    }
}