package edu.seniru.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestResponseLoggingFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Wrap the response to capture the response body
        ResponseWrapper responseWrapper = new ResponseWrapper(response);

        // Continue the filter chain using the wrapped request and response
        filterChain.doFilter(request, responseWrapper);

        // Log the request and response bodies
        logRequestDetails(request);
        logResponseDetails(responseWrapper);

        // After logging, flush the response to the client
        responseWrapper.flushResponse();
    }

    private void logRequestDetails(HttpServletRequest request) throws IOException {
        // Log the request method and URI
        logger.info("Request Info: {} {}", request.getMethod(), request.getRequestURI());
        // Log headers
        StringBuilder headers = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            String headerValue = request.getHeader(headerName);
            headers.append(headerName).append("=").append(headerValue).append(", ");
        });
        if (headers.length() > 0) {
            headers.setLength(headers.length() - 2);  // Remove the last comma
        }
        logger.info("Request Headers: {}", headers.toString());
    }

    private void logResponseDetails(ResponseWrapper responseWrapper) {
        // Log the response body
        String responseBody = responseWrapper.getResponseBody();
        logger.info("Response Body: {}", responseBody);
    }
}
