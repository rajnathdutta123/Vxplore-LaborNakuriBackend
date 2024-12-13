package com.app.LaborNakuriBackend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RequestInterceptor implements HandlerInterceptor {

    // Thread-safe storage for request-response data
    private static final ConcurrentMap<String, RequestResponseData> requestMap = new ConcurrentHashMap<>();

    // This method is called before the request reaches the controller
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Generate a unique request ID for this request
        String requestId = UUID.randomUUID().toString();

        // Create a new RequestResponseData object to store request details
        RequestResponseData requestData = new RequestResponseData();
        requestData.setRequestId(requestId);
        requestData.setStartTime(Instant.now());
        requestData.setLogBuffer(new StringBuilder());

        // Log the request details with the request ID
        requestData.getLogBuffer().append("[Request ID: ").append(requestId).append("] Request received: \n");
        requestData.getLogBuffer().append("[Request ID: ").append(requestId).append("] Method: ").append(request.getMethod()).append("\n");
        requestData.getLogBuffer().append("[Request ID: ").append(requestId).append("] URI: ").append(request.getRequestURI()).append("\n");

        // Add headers to log buffer
        request.getHeaderNames().asIterator().forEachRemaining(header ->
                requestData.getLogBuffer().append("[Request ID: ").append(requestId).append("] ").append(header).append(": ").append(request.getHeader(header)).append("\n")
        );

        // Store the request data in the map with the request ID
        requestMap.put(requestId, requestData);

        // Set the request ID to the response headers for tracking
        response.setHeader("X-Request-ID", requestId);

        // Set the request ID as a request attribute
        request.setAttribute("X-Request-ID", requestId);

        // Continue with the request processing
        return true;
    }

    // This method is called after the controller method has finished execution, but before the view is rendered
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
        // Retrieve the request ID from the request attributes
        String requestId = (String) request.getAttribute("X-Request-ID");

        if (requestId == null) {
            System.out.println("Warning: Request ID is missing. Cannot proceed with logging.");
            return;
        }

        // Retrieve the request data from the map using the request ID
        RequestResponseData requestData = requestMap.get(requestId);

        if (requestData == null) {
            System.out.println("Warning: No request data found for Request ID: " + requestId);
            return;
        }

        // Calculate the processing time
        Instant endTime = Instant.now();
        long processingTime = endTime.toEpochMilli() - requestData.getStartTime().toEpochMilli();

        // Append response status and processing time to the log buffer
        requestData.getLogBuffer().append("[Request ID: ").append(requestId).append("] Response Status: ").append(response.getStatus()).append("\n");
        requestData.getLogBuffer().append("[Request ID: ").append(requestId).append("] Request processed in: ").append(processingTime).append(" ms\n");

        // Add the processing time to the response headers
        response.setHeader("X-Processing-Time", String.valueOf(processingTime));
    }

    // This method is called after the complete request-response cycle (after view rendering)
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Retrieve the request ID from the request attributes
        String requestId = (String) request.getAttribute("X-Request-ID");

        if (requestId == null) {
            System.out.println("Warning: Request ID is missing in afterCompletion. Cannot proceed with logging.");
            return;
        }

        // Retrieve the request data from the map using the request ID
        RequestResponseData requestData = requestMap.get(requestId);

        if (requestData == null) {
            System.out.println("Warning: No request data found for Request ID: " + requestId);
            return;
        }

        // Complete the log and print
        requestData.getLogBuffer().append("[Request ID: ").append(requestId).append("] Request completed.\n");

        // Output the entire log for the request-response cycle
        System.out.println(requestData.getLogBuffer().toString());

        // Remove the request data from the map after logging
        requestMap.remove(requestId);
    }

    // Inner class to hold request-response data
    private static class RequestResponseData {
        private String requestId;
        private Instant startTime;
        private StringBuilder logBuffer;

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public Instant getStartTime() {
            return startTime;
        }

        public void setStartTime(Instant startTime) {
            this.startTime = startTime;
        }

        public StringBuilder getLogBuffer() {
            return logBuffer;
        }

        public void setLogBuffer(StringBuilder logBuffer) {
            this.logBuffer = logBuffer;
        }
    }
}