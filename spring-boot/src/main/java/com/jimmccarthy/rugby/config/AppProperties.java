package com.jimmccarthy.rugby.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties("rugby")
public class AppProperties {
    private final Owner owner = new Owner();
    private final Manager manager = new Manager();
    private final News news = new News();

    public static class Owner extends BasicAuthentication {
    }

    public static class Manager extends BasicAuthentication {
    }

    @Getter
    @Setter
    public static class News {
        private String rssUrl;
        private int messagesToDisplay;
    }

    @Getter
    @Setter
    public abstract static class BasicAuthentication {
        private String username;
        private String password;
    }
}
