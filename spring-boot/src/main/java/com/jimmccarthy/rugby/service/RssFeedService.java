package com.jimmccarthy.rugby.service;

import com.jimmccarthy.rugby.config.AppProperties;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * This service is used to get a RSS feed.
 */
@Service
@Slf4j
public class RssFeedService {
    private static final String BUILD_RSS_FEED_MSG = "Failed to build the RSS feed for %s";
    private final AppProperties properties;

    @Autowired
    public RssFeedService(AppProperties properties) {
        this.properties = properties;
    }

    /**
     * Get the {@link SyndFeed} version of an RSS feed.
     *
     * @return an RSS feed
     * @throws AppServiceException if there was a problem opening the feed
     */
    public SyndFeed getSyndFeed() {
        var rssUrl = properties.getNews().getRssUrl();
        URLConnection urlConnection;
        URL rssFeed;

        try {
            log.info("Opening connection to RSS feed {}", rssUrl);
            rssFeed = URI.create(rssUrl).toURL();

            urlConnection = rssFeed.openConnection();
        } catch (Exception e) {
            throw new AppServiceException(String.format(BUILD_RSS_FEED_MSG, rssUrl), e);
        }

        SyndFeed feed;

        try {
            InputStreamReader isr = createInputStreamReader(urlConnection, rssFeed);
            feed = new SyndFeedInput().build(isr);
        } catch (Exception e) {
            throw new AppServiceException(String.format(BUILD_RSS_FEED_MSG, rssFeed), e);
        }

        log.debug("There are {} entries in the feed", feed.getEntries().size());

        return feed;
    }

    /**
     * This method is provided to allow for JUnit testing.
     *
     * @param urlConnection the URL connection
     * @param rssFeed       the URL
     * @return an {@code InputStreamReader}
     */
    protected InputStreamReader createInputStreamReader(URLConnection urlConnection, URL rssFeed) {
        try {
            return new InputStreamReader(urlConnection.getInputStream());
        } catch (IOException e) {
            throw new AppServiceException(String.format(BUILD_RSS_FEED_MSG, rssFeed), e);
        }
    }
}
