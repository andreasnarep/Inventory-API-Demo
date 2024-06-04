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
public class MainPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBQDoorsByMonth() throws Exception {
        String month = "May";

        ResultActions result = mockMvc.perform(get("/api/main-page/bq-doors/{month}", month));

        result.andExpect(status().isOk());
    }

    @Test
    public void getBQDoorsByMissingMonth() throws Exception {
        String month = "FakeMonth";

        ResultActions result = mockMvc.perform(get("/api/main-page/bq-doors/{month}", month));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void getBQWindowsByMonth() throws Exception {
        String month = "May";

        ResultActions result = mockMvc.perform(get("/api/main-page/bq-windows/{month}", month));

        result.andExpect(status().isOk());
    }

    @Test
    public void getBQWindowsByMissingMonth() throws Exception {
        String month = "FakeMonth";

        ResultActions result = mockMvc.perform(get("/api/main-page/bq-windows/{month}", month));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void getPoloDoorsByMonth() throws Exception {
        String month = "September";

        ResultActions result = mockMvc.perform(get("/api/main-page/polo-doors/{month}", month));

        result.andExpect(status().isOk());
    }

    @Test
    public void getPoloDoorsByMissingMonth() throws Exception {
        String month = "FakeMonth";

        ResultActions result = mockMvc.perform(get("/api/main-page/polo-doors/{month}", month));

        result.andExpect(status().isNotFound());
    }
}
