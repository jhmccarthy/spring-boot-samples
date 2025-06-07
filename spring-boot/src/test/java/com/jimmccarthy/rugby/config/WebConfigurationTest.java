package com.jimmccarthy.rugby.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WebConfigurationTest {
    @Mock
    private ViewControllerRegistry viewControllerRegistry;

    @Mock
    private ViewControllerRegistration viewControllerRegistration;

    @InjectMocks
    private WebConfiguration config;

    @BeforeEach
    public void init() {
        when(viewControllerRegistry.addViewController(anyString())).thenReturn(viewControllerRegistration);
    }

    @Test
    public void addViewControllers() {
        config.addViewControllers(viewControllerRegistry);
        verify(viewControllerRegistry).addViewController("/");
        verify(viewControllerRegistration).setViewName("forward:/index.html");
    }
}
