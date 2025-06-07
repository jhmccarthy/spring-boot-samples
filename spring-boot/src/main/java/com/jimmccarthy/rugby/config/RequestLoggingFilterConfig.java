package com.jimmccarthy.rugby.config;

import com.jimmccarthy.rugby.filter.LoggingFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for logging requests.
 */
@Configuration
public class RequestLoggingFilterConfig {
    /**
     * The logging filter that logs JSON requests.
     *
     * @return the logging filter
     */
    @Bean
    public Filter jsonLoggingFilter() {
        var filter = new LoggingFilter();
        filter.setIncludeQueryString(false);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(2048);
        filter.setIncludeHeaders(false);

        return filter;
    }

    /**
     * The filter registration that logs the JSON requests.
     *
     * @return the filter registration
     */
    @Bean
    public FilterRegistrationBean<Filter> filterRegistration() {
        var registration = new FilterRegistrationBean<>(jsonLoggingFilter());
        registration.addUrlPatterns("/api/v1/rugby/owner", "/api/v1/rugby/mgr");

        return registration;
    }
}
