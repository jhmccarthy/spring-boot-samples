package demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloControllerIT {
    private final TestRestTemplate template;
    @LocalServerPort private int port;
    private URL base;

    @Autowired
    public HelloControllerIT(TestRestTemplate template) {
        this.template = template;
    }

    @BeforeEach
    public void setUp() throws Exception {
        this.base = URI.create("http://localhost:" + port + "/").toURL();
    }

    @Test
    void getHello() {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        assertEquals("Greetings from Spring Boot!", response.getBody());
    }
}
