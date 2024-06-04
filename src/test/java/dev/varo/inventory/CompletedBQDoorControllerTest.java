package dev.varo.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.InventoryItem;
import dev.varo.inventory.services.BQDoorService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CompletedBQDoorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private BQDoorService bqDoorService;

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
     * @param completedBQDoors Doors which were completed
     * @return True  - Inventory items quantities are correct
     *         False - Inventory items quantities aren't correct
     */
    private boolean checkInventoryQuantities(List<CompletedBQDoor> completedBQDoors) {
        // Calculate each item's expected quantity according to the
        // completedBQDoors List and save it here.
        Map<String, Integer> usedMaterialsAndQuantities = new HashMap<>();

        for (CompletedBQDoor completedBQDoor : completedBQDoors) {
            Optional<BQDoor> optionalBQDoor = bqDoorService.bqDoorByName(completedBQDoor.getName());

            if (optionalBQDoor.isPresent()) {
                BQDoor bqDoor = optionalBQDoor.get();

                for (String key : bqDoor.getMaterials().keySet()) { // These are the items which were used to create the door
                    Optional<InventoryItem> optionalInventoryItem = inventoryService.inventoryItemByName(key);

                    if (optionalInventoryItem.isPresent()) {
                        InventoryItem inventoryItem = optionalInventoryItem.get();

                        if (usedMaterialsAndQuantities.containsKey(key)) {
                            usedMaterialsAndQuantities.replace(key, usedMaterialsAndQuantities.get(key) - (bqDoor.getMaterials().get(key) * completedBQDoor.getQuantity()));
                        } else {
                            // We subtract from 100, because that is the quantity,
                            // which is set before each test case.
                            usedMaterialsAndQuantities.put(key, 100 - bqDoor.getMaterials().get(key) * completedBQDoor.getQuantity());
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
    public void addOneCompletedBQDoor() throws Exception {
        CompletedBQDoor completedBQDoor = new CompletedBQDoor();
        completedBQDoor.setName("1690x1900 1/1");
        completedBQDoor.setQuantity(1);
        completedBQDoor.setMonth("August");

        List<CompletedBQDoor> completedBQDoors = new ArrayList<>();
        completedBQDoors.add(completedBQDoor);

        String requestBody = objectMapper.writeValueAsString(completedBQDoors);

        ResultActions result = mockMvc.perform(post("/api/completed-bq-doors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("All BQ Doors added successfully"));

        assertTrue(checkInventoryQuantities(completedBQDoors));
    }

    @Test
    public void addMultipleCompletedBQDoorsWithSameName() throws Exception {
        CompletedBQDoor completedBQDoor = new CompletedBQDoor();
        completedBQDoor.setName("1690x1900 1/1");
        completedBQDoor.setQuantity(1);
        completedBQDoor.setMonth("July");

        CompletedBQDoor completedBQDoor2 = new CompletedBQDoor();
        completedBQDoor2.setName("1690x1900 1/1");
        completedBQDoor2.setQuantity(3);
        completedBQDoor2.setMonth("June");

        List<CompletedBQDoor> completedBQDoors = new ArrayList<>();
        completedBQDoors.add(completedBQDoor);
        completedBQDoors.add(completedBQDoor2);

        String requestBody = objectMapper.writeValueAsString(completedBQDoors);

        ResultActions result = mockMvc.perform(post("/api/completed-bq-doors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("All BQ Doors added successfully"));

        assertTrue(checkInventoryQuantities(completedBQDoors));
    }

    @Test
    public void addMultipleCompletedBQDoorsWithDifferentName() throws Exception {
        CompletedBQDoor completedBQDoor = new CompletedBQDoor();
        completedBQDoor.setName("1690x1900 1/1");
        completedBQDoor.setQuantity(1);
        completedBQDoor.setMonth("June");

        CompletedBQDoor completedBQDoor2 = new CompletedBQDoor();
        completedBQDoor2.setName("1599x2965 1/2");
        completedBQDoor2.setQuantity(3);
        completedBQDoor2.setMonth("June");

        List<CompletedBQDoor> completedBQDoors = new ArrayList<>();
        completedBQDoors.add(completedBQDoor);
        completedBQDoors.add(completedBQDoor2);

        String requestBody = objectMapper.writeValueAsString(completedBQDoors);

        ResultActions result = mockMvc.perform(post("/api/completed-bq-doors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("All BQ Doors added successfully"));

        assertTrue(checkInventoryQuantities(completedBQDoors));
    }

    @Test
    public void addCompletedBQDoorThatDontExist() throws Exception {
        CompletedBQDoor completedBQDoor = new CompletedBQDoor();
        completedBQDoor.setName("dontexist");
        completedBQDoor.setQuantity(1);
        completedBQDoor.setMonth("June");

        List<CompletedBQDoor> completedBQDoors = new ArrayList<>();
        completedBQDoors.add(completedBQDoor);

        String requestBody = objectMapper.writeValueAsString(completedBQDoors);

        ResultActions result = mockMvc.perform(post("/api/completed-bq-doors/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isOk())
                .andExpect(content().string("Some BQ Doors weren't added due to not being present"));

        assertTrue(checkInventoryQuantities(completedBQDoors));
    }
}
