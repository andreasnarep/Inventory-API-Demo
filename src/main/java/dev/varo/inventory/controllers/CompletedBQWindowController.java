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
import java.util.ArrayList;
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
        List<CompletedBQWindow> windowsToRemove = new ArrayList<>();

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
                        inventoryService.updateInventoryItem(inventoryItem);
                    }
                }
            } else {
                windowsToRemove.add(completedBQWindow);
            }
        }

        for (CompletedBQWindow completedBQWindow : windowsToRemove) {
            completedBQWindows.remove(completedBQWindow);
        }
        completedBQWindowService.addCompletedBQWindows(completedBQWindows);

        if (!windowsToRemove.isEmpty()) {
            return ResponseEntity.ok("Some BQ Windows weren't added due to not being present");
        }

        return ResponseEntity.ok("All BQ Windows added successfully");
    }
}
