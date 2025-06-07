package com.jimmccarthy.rugby.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

@Configuration
public class HttpMessageConverterConfig {
    /**
     * Convert responses returned by {@code @RequestMapping} annotated methods to protocol buffer messages.
     *
     * @return the HTTP message converter
     */
    @Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }
}
