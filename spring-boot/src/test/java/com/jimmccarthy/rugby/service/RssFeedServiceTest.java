package com.jimmccarthy.rugby.service;

import com.jimmccarthy.rugby.config.AppProperties;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RssFeedServiceTest {
    @Spy
    private static AppProperties properties;

    @Spy
    @InjectMocks
    private RssFeedService service;

    @BeforeAll
    static void setup() {
        properties = new AppProperties();

        properties.getNews().setRssUrl("http://rssUrl");
        properties.getNews().setMessagesToDisplay(3);
    }

    @BeforeEach
    public void init() throws Exception {
        var rssStream = new ByteArrayInputStream(getFeed().getBytes());

        /*
         * The when(mock.method()).thenReturn(value) syntax was actually calling the method and mocking the method. This
         * syntax is working in this situation.
         */
        doReturn(new InputStreamReader(rssStream)).when(service)
                .createInputStreamReader(any(URLConnection.class), any(URL.class));
    }

    @Test
    public void syndFeed() throws Exception {
        var feed = service.getSyndFeed();
        assertNotNull(feed);
        assertEquals(1, feed.getEntries().size());
    }

    private String getFeed() throws FeedException {
        var feed = new SyndFeedImpl();
        feed.setFeedType("atom_1.0");

        var entry = new SyndEntryImpl();
        entry.setTitle("Sample feed title");

        var content = new SyndContentImpl();
        content.setValue("Sample feed content");

        entry.setContents(Collections.singletonList(content));
        feed.setEntries(Collections.singletonList(entry));

        return new SyndFeedOutput().outputString(feed);
    }
}
