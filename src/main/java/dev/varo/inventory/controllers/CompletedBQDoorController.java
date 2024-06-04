package dev.varo.inventory.controllers;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.InventoryItem;
import dev.varo.inventory.services.BQDoorService;
import dev.varo.inventory.services.CompletedBQDoorService;
import dev.varo.inventory.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/completed-bq-doors")
@CrossOrigin(origins = "*")
public class CompletedBQDoorController {
    @Autowired
    private CompletedBQDoorService completedBQDoorService;
    @Autowired
    private BQDoorService bqDoorService;
    @Autowired
    private InventoryService inventoryService;


    @PostMapping("/add")
    public ResponseEntity<String> addBQDoors(@RequestBody List<CompletedBQDoor> completedBQDoors) throws ParseException, ChangeSetPersister.NotFoundException {
        List<CompletedBQDoor> doorsToRemove = new ArrayList<>();

        for (CompletedBQDoor completedBQDoor : completedBQDoors) {
            completedBQDoor.createDate();
            Optional<BQDoor> optionalBQDoor = bqDoorService.bqDoorByName(completedBQDoor.getName());

            if (optionalBQDoor.isPresent()) {
                BQDoor bqDoor = optionalBQDoor.get();

                for (String key : bqDoor.getMaterials().keySet()) {
                    Optional<InventoryItem> optionalInventoryItem = inventoryService.inventoryItemByName(key);

                    if (optionalInventoryItem.isPresent()) {
                        InventoryItem inventoryItem = optionalInventoryItem.get();
                        String inventoryItemName = inventoryItem.getName();
                        int oldQuantity = inventoryItem.getQuantity();
                        int newQuantity = inventoryItem.getQuantity() - (bqDoor.getMaterials().get(key) * completedBQDoor.getQuantity());

                        inventoryItem.setQuantity(newQuantity);
                        inventoryService.updateInventoryItemQuantity(inventoryItem);
                    }
                }
            } else {
                doorsToRemove.add(completedBQDoor);
            }
        }

        for (CompletedBQDoor completedBQDoor : doorsToRemove) {
            completedBQDoors.remove(completedBQDoor);
        }

        completedBQDoorService.addCompletedBQDoors(completedBQDoors);

        if (!doorsToRemove.isEmpty()) {
            return ResponseEntity.ok("Some BQ Doors weren't added due to not being present");
        }

        return ResponseEntity.ok("All BQ Doors added successfully");
    }
}
