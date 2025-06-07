package com.jimmccarthy.rugby.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@SpringJUnitConfig
public class AppPropertiesTest {
    private final AppProperties properties;

    @Autowired
    public AppPropertiesTest(AppProperties properties) {
        this.properties = properties;
    }

    @Test
    public void news() {
        var news = properties.getNews();
        assertThat(news.getRssUrl(), is("https://www.rnz.co.nz/rss/sport.xml"));
        assertThat(news.getMessagesToDisplay(), is(5));
    }

    @Test
    public void ownerUser() {
        var owner = properties.getOwner();
        assertThat(owner.getUsername(), is("ownr1"));
        assertThat(owner.getPassword(), is("$2a$10$UZBULM3B5qA2bIljHJULoOUMooxtmzwvbF4nraPURkA/DTtnTEKe2"));
    }

    @Test
    public void managerUser() {
        var mgr = properties.getManager();
        assertThat(mgr.getUsername(), is("mgr1"));
        assertThat(mgr.getPassword(), is("$2a$10$Cs7ndaORjL.WIAWa/NmZ8.SSk5OFYKrZNAf8Yla/uZHqceQJYT7MO"));
    }
}
