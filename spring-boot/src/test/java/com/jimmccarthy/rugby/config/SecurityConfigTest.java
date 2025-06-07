package com.jimmccarthy.rugby.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(MockitoExtension.class)
public class SecurityConfigTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfigTest.class);
    private static final String RAW_PASSWORD = "pa$$Word1";
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void generateEncryptedPassword() {
        var encodedPassword = passwordEncoder.encode(RAW_PASSWORD);
        assertThat(encodedPassword, is(notNullValue()));
        LOGGER.info("Encoded password is {}", encodedPassword);
    }

    @Test
    public void verifyPasswordMatchesEncryptedPassword() {
        var encodedPassword = passwordEncoder.encode(RAW_PASSWORD);
        assertThat(encodedPassword, is(notNullValue()));

        var matches = passwordEncoder.matches(RAW_PASSWORD, encodedPassword);
        assertThat(matches, is(true));
    }
}
