package dev.varo.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BQWindowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllBQWindows() throws Exception {
        ResultActions result = mockMvc.perform(get("/api/bq-windows"));

        result.andExpect(status().isOk());
    }
}
