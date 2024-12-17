package com.app.LaborNakuriBackend.filter;

import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class SwaggerAuthFilter implements Filter {

    private static final String SWAGGER_USERNAME = "admin";
    private static final String SWAGGER_PASSWORD = "admin123";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        // Apply filter for Swagger UI and API docs
        if (requestURI.contains("swagger-ui") || requestURI.contains("v3/api-docs")) {
            String authHeader = httpRequest.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Basic ")) {
                sendUnauthorizedResponse(httpResponse);
                return;
            }

            // Decode credentials
            String base64Credentials = authHeader.substring("Basic".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));
            String[] values = credentials.split(":", 2);

            if (values.length == 2 && SWAGGER_USERNAME.equals(values[0]) && SWAGGER_PASSWORD.equals(values[1])) {
                chain.doFilter(request, response); // Proceed
            } else {
                sendUnauthorizedResponse(httpResponse);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void sendUnauthorizedResponse(HttpServletResponse response) throws IOException {
        response.setHeader("WWW-Authenticate", "Basic realm=\"Swagger API Docs\"");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized");
    }

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void destroy() {}
}