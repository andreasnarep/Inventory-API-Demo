package dev.varo.inventory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getInventoryItemByName() throws Exception {
        String name = "605x1537";

        ResultActions result = mockMvc.perform(get("/api/inventory/{name}", name));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("605x1537"));
    }

    @Test
    public void getMissingInventoryItemByName() throws Exception {
        String name = "random";

        ResultActions result = mockMvc.perform(get("/api/inventory/{name}", name));

        result.andExpect(status().isNotFound());
    }
}
