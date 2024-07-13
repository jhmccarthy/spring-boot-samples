package demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {
    private static final String GREETINGS = "Greetings from Spring Boot!";
    private final MockMvc mvc;

    @Autowired
    public HelloControllerTest(MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void index() {
        var controller = new HelloController();
        assertEquals(GREETINGS, controller.index());
    }

    @Test
    void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/") //
                        .accept(MediaType.APPLICATION_JSON)) //
                .andExpect(status().isOk()) //
                .andExpect(content().string(equalTo(GREETINGS)));
    }
}
