package dev.varo.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.varo.inventory.objects.*;
import dev.varo.inventory.services.BQWindowService;
import dev.varo.inventory.services.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CompletedBQWindowControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BQWindowService bqWindowService;

    @BeforeEach
    public void setUpInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryService.allInventoryItems();

        for (InventoryItem item : inventoryItems) {
            item.setQuantity(100);
            inventoryService.updateInventoryItem(item);
        }
    }

    /**
     * Checks if the inventory items quantities were altered correctly
     * after subtracting the materials necessary for the completed windows.
     *
     * @param completedBQWindows Doors which were completed
     * @return True  - Inventory items quantities are correct
     *         False - Inventory items quantities aren't correct
     */
    private boolean checkInventoryQuantities(List<CompletedBQWindow> completedBQWindows) {
        // Calculate each item's expected quantity according to the
        // completedBQWindows List and save it here.
        Map<String, Integer> usedMaterialsAndQuantities = new HashMap<>();

        for (CompletedBQWindow completedBQWindow : completedBQWindows) {
            Optional<BQWindow> optionalBQWindow = bqWindowService.bqWindowByName(completedBQWindow.getName());

            if (optionalBQWindow.isPresent()) {
                BQWindow bqWindow = optionalBQWindow.get();

                for (String key : bqWindow.getMaterials().keySet()) { // These are the items which were used to create the door
                    Optional<InventoryItem> optionalInventoryItem = inventoryService.inventoryItemByName(key);

                    if (optionalInventoryItem.isPresent()) {
                        InventoryItem inventoryItem = optionalInventoryItem.get();

                        if (usedMaterialsAndQuantities.containsKey(key)) {
                            usedMaterialsAndQuantities.replace(key, usedMaterialsAndQuantities.get(key) - (bqWindow.getMaterials().get(key) * completedBQWindow.getQuantity()));
                        } else {
                            // We subtract from 100, because that is the quantity,
                            // which is set before each test case.
                            usedMaterialsAndQuantities.put(key, 100 - bqWindow.getMaterials().get(key) * completedBQWindow.getQuantity());
                        }
                    }
                }
            }
        }

        for (InventoryItem item : inventoryService.allInventoryItems()) {
            if (usedMaterialsAndQuantities.containsKey(item.getName())) {
                if (usedMaterialsAndQuantities.get(item.getName()) != item.getQuantity()) {
                    return false;
                }
            } else if (item.getQuantity() != 100) {
                return false;
            }
        }

        return true;
    }


    @Test
    public void addOneCompletedBQWindow() throws Exception {
        CompletedBQWindow completedBQWindow = new CompletedBQWindow();
        completedBQWindow.setName("1390x1000");
        completedBQWindow.setQuantity(1);
        completedBQWindow.setMonth("October");

        List<CompletedBQWindow> completedBQWindows = new ArrayList<>();
        completedBQWindows.add(completedBQWindow);

        String requestBody = objectMapper.writeValueAsString(completedBQWindows);

        ResultActions result = mockMvc.perform(post("/api/completed-bq-windows/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("All BQ Windows added successfully"));

        assertTrue(checkInventoryQuantities(completedBQWindows));
    }

    @Test
    public void addMultipleCompletedBQWindowsWithSameName() throws Exception {
        CompletedBQWindow completedBQWindow = new CompletedBQWindow();
        completedBQWindow.setName("1390x1000");
        completedBQWindow.setQuantity(1);
        completedBQWindow.setMonth("June");

        CompletedBQWindow completedBQWindow2 = new CompletedBQWindow();
        completedBQWindow2.setName("1390x1000");
        completedBQWindow2.setQuantity(2);
        completedBQWindow2.setMonth("August");

        List<CompletedBQWindow> completedBQWindows = new ArrayList<>();
        completedBQWindows.add(completedBQWindow);
        completedBQWindows.add(completedBQWindow2);

        String requestBody = objectMapper.writeValueAsString(completedBQWindows);

        ResultActions result = mockMvc.perform(post("/api/completed-bq-windows/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("All BQ Windows added successfully"));

        assertTrue(checkInventoryQuantities(completedBQWindows));
    }

    @Test
    public void addMultipleCompletedBQWindowsWithDifferentName() throws Exception {
        CompletedBQWindow completedBQWindow = new CompletedBQWindow();
        completedBQWindow.setName("1390x1000");
        completedBQWindow.setQuantity(10);
        completedBQWindow.setMonth("May");

        CompletedBQWindow completedBQWindow2 = new CompletedBQWindow();
        completedBQWindow2.setName("1290x1000");
        completedBQWindow2.setQuantity(2);
        completedBQWindow2.setMonth("March");

        List<CompletedBQWindow> completedBQWindows = new ArrayList<>();
        completedBQWindows.add(completedBQWindow);
        completedBQWindows.add(completedBQWindow2);

        String requestBody = objectMapper.writeValueAsString(completedBQWindows);

        ResultActions result = mockMvc.perform(post("/api/completed-bq-windows/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("All BQ Windows added successfully"));

        assertTrue(checkInventoryQuantities(completedBQWindows));
    }

    @Test
    public void addCompletedBQWindowThatDontExist() throws Exception {
        CompletedBQWindow completedBQWindow = new CompletedBQWindow();
        completedBQWindow.setName("dontexist");
        completedBQWindow.setQuantity(10);
        completedBQWindow.setMonth("December");

        List<CompletedBQWindow> completedBQWindows = new ArrayList<>();
        completedBQWindows.add(completedBQWindow);

        String requestBody = objectMapper.writeValueAsString(completedBQWindows);

        ResultActions result = mockMvc.perform(post("/api/completed-bq-windows/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("Some BQ Windows weren't added due to not being present"));

        assertTrue(checkInventoryQuantities(completedBQWindows));
    }
}