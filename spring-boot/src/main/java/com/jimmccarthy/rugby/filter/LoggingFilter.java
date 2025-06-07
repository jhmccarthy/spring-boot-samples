package com.jimmccarthy.rugby.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

/**
 * A logging filter that write the request URIL to the log so that we can see the JSON request. The JSON request is
 * written after the request is processed.
 */
@Component
@Slf4j
public class LoggingFilter extends AbstractRequestLoggingFilter {
    /**
     * {@inheritDoc}
     */
    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        // The message doesn't contain the JSON request
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        log.info(message);
    }
}
