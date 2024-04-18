package dev.varo.inventory.controllers;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.InventoryItem;
import dev.varo.inventory.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@CrossOrigin(origins = "*")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryItem>> getAllInventoryItems() {
        return new ResponseEntity<>(inventoryService.allInventoryItems(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<InventoryItem>> getSingleInventoryItem(@PathVariable String name) {
        return new ResponseEntity<>(inventoryService.inventoryItemByName(name), HttpStatus.OK);
    }

    @PostMapping("/change-quantity")
    public ResponseEntity<String> addBQDoors(@RequestBody List<InventoryItem> inventoryItems) throws ParseException, ChangeSetPersister.NotFoundException {


        Optional<InventoryItem> optionalInventoryItem = inventoryService.inventoryItemByName(inventoryItems.get(0).getName());
        InventoryItem inventoryItem = inventoryItems.get(0);

        if (optionalInventoryItem.isPresent()) {
            InventoryItem databaseInventoryItem = optionalInventoryItem.get();
            String inventoryItemName = databaseInventoryItem.getName();
            int oldQuantity = databaseInventoryItem.getQuantity();
            int newQuantity = inventoryItem.getQuantity();

            databaseInventoryItem.setQuantity(newQuantity);
            inventoryService.updateInventoryItemQuantity(databaseInventoryItem);
            System.out.println(String.format("INVENTORY ITEM NAME: %s\tOLD QUANTITY: %s\t" +
                    "NEW QUANTITY: %s", inventoryItemName, oldQuantity, newQuantity));
        }

        return ResponseEntity.ok("Quantity changed succesfully");
    }
}


