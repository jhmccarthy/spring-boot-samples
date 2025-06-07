package com.jimmccarthy.rugby.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

/**
 * Redirect HTTP (8080) traffic to HTTPS (8443).
 */
//@Configuration
public class TomcatConfig {
    @Bean
    public ServletWebServerFactory servletContainer() {
        // Add an HTTP connector at port 8080 to redirect traffic to port 8443.
        var tomcat = new TomcatServletWebServerFactory() {
            /**
             * {@inheritDoc}
             */
            @Override
            protected void postProcessContext(Context context) {
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");

                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                constraint.addCollection(collection);

                context.addConstraint(constraint);
            }
        };

        tomcat.addAdditionalTomcatConnectors(redirectConnector());

        return tomcat;
    }

    private Connector redirectConnector() {
        var connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);

        return connector;
    }
}
