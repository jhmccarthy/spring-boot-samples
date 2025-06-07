package com.jimmccarthy.rugby.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configure Spring Boot using an Angular client.
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private static final String INDEX_VIEW_NAME = "forward:/index.html";

    /**
     * {@inheritDoc}
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName(INDEX_VIEW_NAME);
    }
}
