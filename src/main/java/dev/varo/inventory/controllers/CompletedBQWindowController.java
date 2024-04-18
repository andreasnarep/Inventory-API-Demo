package dev.varo.inventory.controllers;

import dev.varo.inventory.objects.*;
import dev.varo.inventory.services.BQWindowService;
import dev.varo.inventory.services.CompletedBQWindowService;
import dev.varo.inventory.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/completed-bq-windows")
@CrossOrigin(origins = "*")
public class CompletedBQWindowController {
    @Autowired
    private CompletedBQWindowService completedBQWindowService;
    @Autowired
    private BQWindowService bqWindowService;
    @Autowired
    private InventoryService inventoryService;


    @PostMapping("/add")
    public ResponseEntity<String> addBQWindows(@RequestBody List<CompletedBQWindow> completedBQWindows) throws ParseException, ChangeSetPersister.NotFoundException {
        for (CompletedBQWindow completedBQWindow : completedBQWindows) {
            completedBQWindow.createDate();
            Optional<BQWindow> optionalBQWindow = bqWindowService.bqWindowByName(completedBQWindow.getName());

            if (optionalBQWindow.isPresent()) {
                BQWindow bqWindow = optionalBQWindow.get();

                for (String key : bqWindow.getMaterials().keySet()) {
                    Optional<InventoryItem> optionalInventoryItem = inventoryService.inventoryItemByName(key);

                    if (optionalInventoryItem.isPresent()) {
                        InventoryItem inventoryItem = optionalInventoryItem.get();
                        String inventoryItemName = inventoryItem.getName();
                        int oldQuantity = inventoryItem.getQuantity();
                        int newQuantity = inventoryItem.getQuantity() - (bqWindow.getMaterials().get(key) * completedBQWindow.getQuantity());

                        inventoryItem.setQuantity(newQuantity);
                        inventoryService.updateInventoryItemQuantity(inventoryItem);
                        System.out.println(String.format("INVENTORY ITEM NAME: %s\tOLD QUANTITY: %s\t" +
                                "NEW QUANTITY: %s", inventoryItemName, oldQuantity, newQuantity));
                    }
                }
            }
        }

        System.out.println(completedBQWindows);
        completedBQWindowService.addCompletedBQWindows(completedBQWindows);

        return ResponseEntity.ok("BQ Doors added successfully");
    }
}
