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
        Optional<InventoryItem> item = inventoryService.inventoryItemByName(name);

        if (item.isEmpty()) {
            return new ResponseEntity<>(item, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}


