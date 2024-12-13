//package com.app.LaborNakuriBackend.filter;
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//
//@WebFilter("/*") // Apply to all requests
//public class RequestResponseFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Initialization code (if needed)
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        // Capture request data
//        System.out.println("Request Method: " + httpRequest.getMethod());
//        System.out.println("Request URI: " + httpRequest.getRequestURI());
//        System.out.println("Request Headers: ");
//        httpRequest.getHeaderNames().asIterator().forEachRemaining(header ->
//                System.out.println(header + ": " + httpRequest.getHeader(header))
//        );
//
//        // Proceed to next filter or controller
//        chain.doFilter(request, response);
//
//        // Capture response data
//        System.out.println("Response Status: " + httpResponse.getStatus());
//    }
//
//    @Override
//    public void destroy() {
//        // Cleanup code (if needed)
//    }
//}
