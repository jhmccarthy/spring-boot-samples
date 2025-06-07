package com.jimmccarthy.rugby.config;

import com.jimmccarthy.rugby.filter.LoggingFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@ExtendWith(MockitoExtension.class)
public class RequestLoggingFilterConfigTest {
    private final RequestLoggingFilterConfig config = new RequestLoggingFilterConfig();

    @Test
    public void jsonLoggingFilter() {
        var filter = config.jsonLoggingFilter();
        assertThat(filter, instanceOf(LoggingFilter.class));
    }

    @Test
    public void filterRegistration() {
        var registration = config.filterRegistration();

        var urlPatterns = registration.getUrlPatterns();
        assertThat(urlPatterns, hasSize(2));
        assertThat(urlPatterns.toArray()[0], is("/api/v1/rugby/owner"));
        assertThat(urlPatterns.toArray()[1], is("/api/v1/rugby/mgr"));
    }
}
