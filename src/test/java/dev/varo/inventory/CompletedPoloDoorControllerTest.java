package dev.varo.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.varo.inventory.objects.*;
import dev.varo.inventory.services.InventoryService;
import dev.varo.inventory.services.PoloDoorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompletedPoloDoorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private PoloDoorService poloDoorService;

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
     * after subtracting the materials necessary for the completed doors.
     *
     * @param completedPoloDoors Doors which were completed
     * @return True  - Inventory items quantities are correct
     *         False - Inventory items quantities aren't correct
     */
    private boolean checkInventoryQuantities(List<CompletedPoloDoor> completedPoloDoors) {
        // Calculate each item's expected quantity according to the
        // completedPoloDoors List and save it here.
        Map<String, Integer> usedMaterialsAndQuantities = new HashMap<>();

        for (CompletedPoloDoor completedPoloDoor : completedPoloDoors) {
            Optional<PoloDoor> optionalPoloDoor = poloDoorService.poloDoorByName(completedPoloDoor.getName());

            if (optionalPoloDoor.isPresent()) {
                PoloDoor poloDoor = optionalPoloDoor.get();

                for (String key : poloDoor.getMaterials().keySet()) { // These are the items which were used to create the door
                    Optional<InventoryItem> optionalInventoryItem = inventoryService.inventoryItemByName(key);

                    if (optionalInventoryItem.isPresent()) {
                        InventoryItem inventoryItem = optionalInventoryItem.get();

                        if (usedMaterialsAndQuantities.containsKey(key)) {
                            usedMaterialsAndQuantities.replace(key, usedMaterialsAndQuantities.get(key) - (poloDoor.getMaterials().get(key) * completedPoloDoor.getQuantity()));
                        } else {
                            // We subtract from 100, because that is the quantity,
                            // which is set before each test case.
                            usedMaterialsAndQuantities.put(key, 100 - poloDoor.getMaterials().get(key) * completedPoloDoor.getQuantity());
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
    public void addOneCompletedPoloDoor() throws Exception {
        CompletedPoloDoor completedPoloDoor = new CompletedPoloDoor();
        completedPoloDoor.setName("1690x1900 1/1");
        completedPoloDoor.setQuantity(3);
        completedPoloDoor.setMonth("July");

        List<CompletedPoloDoor> completedPoloDoors = new ArrayList<>();
        completedPoloDoors.add(completedPoloDoor);

        String requestBody = objectMapper.writeValueAsString(completedPoloDoors);

        ResultActions result = mockMvc.perform(post("/api/completed-polo-doors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("All Polo Doors added successfully"));

        assertTrue(checkInventoryQuantities(completedPoloDoors));
    }

    @Test
    public void addMultipleCompletedPoloDoorsWithSameName() throws Exception {
        CompletedPoloDoor completedPoloDoor = new CompletedPoloDoor();
        completedPoloDoor.setName("1690x1900 1/1");
        completedPoloDoor.setQuantity(3);
        completedPoloDoor.setMonth("March");

        CompletedPoloDoor completedPoloDoor2 = new CompletedPoloDoor();
        completedPoloDoor2.setName("1690x1900 1/1");
        completedPoloDoor2.setQuantity(2);
        completedPoloDoor2.setMonth("February");

        List<CompletedPoloDoor> completedPoloDoors = new ArrayList<>();
        completedPoloDoors.add(completedPoloDoor);
        completedPoloDoors.add(completedPoloDoor2);

        String requestBody = objectMapper.writeValueAsString(completedPoloDoors);

        ResultActions result = mockMvc.perform(post("/api/completed-polo-doors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("All Polo Doors added successfully"));

        assertTrue(checkInventoryQuantities(completedPoloDoors));
    }

    @Test
    public void addMultipleCompletedPoloDoorsWithDifferentName() throws Exception {
        CompletedPoloDoor completedPoloDoor = new CompletedPoloDoor();
        completedPoloDoor.setName("1690x1900 1/1");
        completedPoloDoor.setQuantity(3);
        completedPoloDoor.setMonth("January");

        CompletedPoloDoor completedPoloDoor2 = new CompletedPoloDoor();
        completedPoloDoor2.setName("1690x2050 1/1");
        completedPoloDoor2.setQuantity(5);
        completedPoloDoor2.setMonth("October");

        List<CompletedPoloDoor> completedPoloDoors = new ArrayList<>();
        completedPoloDoors.add(completedPoloDoor);
        completedPoloDoors.add(completedPoloDoor2);

        String requestBody = objectMapper.writeValueAsString(completedPoloDoors);

        ResultActions result = mockMvc.perform(post("/api/completed-polo-doors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("All Polo Doors added successfully"));

        assertTrue(checkInventoryQuantities(completedPoloDoors));
    }

    @Test
    public void addCompletedPoloDoorThatDontExist() throws Exception {
        CompletedPoloDoor completedPoloDoor = new CompletedPoloDoor();
        completedPoloDoor.setName("dontexist");
        completedPoloDoor.setQuantity(1);
        completedPoloDoor.setMonth("July");

        List<CompletedPoloDoor> completedPoloDoors = new ArrayList<>();
        completedPoloDoors.add(completedPoloDoor);

        String requestBody = objectMapper.writeValueAsString(completedPoloDoors);

        ResultActions result = mockMvc.perform(post("/api/completed-polo-doors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("Some Polo Doors weren't added due to not being present"));

        assertTrue(checkInventoryQuantities(completedPoloDoors));
    }
}
