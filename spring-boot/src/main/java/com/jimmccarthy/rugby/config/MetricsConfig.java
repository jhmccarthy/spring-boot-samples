package com.jimmccarthy.rugby.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class MetricsConfig {
    @Value("$(spring.application:name:demo}")
    String applicationName;

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() throws UnknownHostException {
        final var hostName = InetAddress.getLocalHost().getHostName();

        return registry -> registry.config().commonTags("service.name", applicationName, "service.host",
                hostName);
    }
}
