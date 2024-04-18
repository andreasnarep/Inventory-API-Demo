package dev.varo.inventory.controllers;

import dev.varo.inventory.objects.*;
import dev.varo.inventory.services.CompletedPoloDoorService;
import dev.varo.inventory.services.InventoryService;
import dev.varo.inventory.services.PoloDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/completed-polo-doors")
@CrossOrigin(origins = "*")
public class CompletedPoloDoorController {
    @Autowired
    private CompletedPoloDoorService completedPoloDoorService;
    @Autowired
    private PoloDoorService poloDoorService;
    @Autowired
    private InventoryService inventoryService;


    @PostMapping("/add")
    public ResponseEntity<String> addPoloDoors(@RequestBody List<CompletedPoloDoor> completedPoloDoors) throws ParseException, ChangeSetPersister.NotFoundException {
        for (CompletedPoloDoor completedPoloDoor : completedPoloDoors) {
            completedPoloDoor.createDate();
            Optional<PoloDoor> optionalPoloDoor = poloDoorService.poloDoorByName(completedPoloDoor.getName());

            if (optionalPoloDoor.isPresent()) {
                PoloDoor poloDoor = optionalPoloDoor.get();

                for (String key : poloDoor.getMaterials().keySet()) {
                    Optional<InventoryItem> optionalInventoryItem = inventoryService.inventoryItemByName(key);

                    if (optionalInventoryItem.isPresent()) {
                        InventoryItem inventoryItem = optionalInventoryItem.get();
                        String inventoryItemName = inventoryItem.getName();
                        int oldQuantity = inventoryItem.getQuantity();
                        int newQuantity = inventoryItem.getQuantity() - (poloDoor.getMaterials().get(key) * completedPoloDoor.getQuantity());

                        inventoryItem.setQuantity(newQuantity);
                        inventoryService.updateInventoryItemQuantity(inventoryItem);
                        System.out.println(String.format("INVENTORY ITEM NAME: %s\tOLD QUANTITY: %s\t" +
                                "NEW QUANTITY: %s", inventoryItemName, oldQuantity, newQuantity));
                    }
                }
            }
        }

        System.out.println(completedPoloDoors);
        completedPoloDoorService.addCompletedPoloDoors(completedPoloDoors);

        return ResponseEntity.ok("Polo Doors added successfully");
    }
}
