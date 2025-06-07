package com.jimmccarthy.rugby.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

@ExtendWith(MockitoExtension.class)
public class TomcatConfigTest {
    private final TomcatConfig config = new TomcatConfig();

    @Test
    public void tomcatFactory() {
        var container = config.servletContainer();
        assertThat(container, is(notNullValue()));
        assertThat(container, instanceOf(TomcatServletWebServerFactory.class));

        var tomcat = (TomcatServletWebServerFactory) container;
        assertThat(tomcat, is(notNullValue()));
        assertThat(tomcat.getAdditionalTomcatConnectors(), hasSize(1));

        var connector = tomcat.getAdditionalTomcatConnectors().getFirst();
        assertThat(connector.getScheme(), is("http"));
        assertThat(connector.getPort(), is(8080));
        assertThat(connector.getSecure(), is(false));
        assertThat(connector.getRedirectPort(), is(8443));
    }
}
