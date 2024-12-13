package com.app.LaborNakuriBackend.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // Capture request data before controller execution
        System.out.println("Pre Handle - Request Method: " + request.getMethod());
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Request Headers: ");
        request.getHeaderNames().asIterator().forEachRemaining(header ->
                System.out.println(header + ": " + request.getHeader(header))
        );
        return true; // Continue to controller
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
        // Capture response data after controller execution
        System.out.println("Post Handle - Response Status: " + response.getStatus());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        // Log after the completion of request processing
        System.out.println("After Completion");
    }
}