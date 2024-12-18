package com.app.LaborNakuriBackend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception exception) {
        ProblemDetail errorDetail;

        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");
        } else if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");
        } else if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not authorized to access this resource");
        } else if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");
        } else if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");
        }
        else if (exception instanceof NoResourceFoundException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Resource Not Found");
        }
        else if (exception instanceof MissingCsrfTokenException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Missing Csrf Token");
        }
        else if (exception instanceof InvalidCsrfTokenException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "Invalid Csrf Token");
        }
        else {
            // Dynamically decide the status code for unhandled exceptions.
            HttpStatusCode status = HttpStatusCode.valueOf(determineHttpStatus(exception));
            errorDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
            errorDetail.setProperty("description", "An error occurred: " + status);
        }

        return errorDetail;
    }

    private int determineHttpStatus(Exception exception) {
        // Check for @ResponseStatus annotation
        ResponseStatus statusAnnotation = exception.getClass().getAnnotation(ResponseStatus.class);
        if (statusAnnotation != null) {
            return statusAnnotation.value().value();
        }

        // Check for specific Spring exceptions
        if (exception instanceof ResponseStatusException) {
            return ((ResponseStatusException) exception).getStatusCode().value();
        }
        else if (exception instanceof IllegalArgumentException) {
            return 400; // Bad Request
        } else if (exception instanceof org.springframework.security.access.AccessDeniedException) {
            return 403; // Forbidden
        } else if (exception instanceof HttpRequestMethodNotSupportedException) {
            return 405; // Method Not Allowed
        }

        // Default to Internal Server Error
        return 500;
    }
}
