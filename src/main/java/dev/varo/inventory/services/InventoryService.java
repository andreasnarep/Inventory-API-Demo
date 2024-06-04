package dev.varo.inventory.services;

import dev.varo.inventory.objects.InventoryItem;
import dev.varo.inventory.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryItem> allInventoryItems() {
        return inventoryRepository.findAll();
    }

    public Optional<InventoryItem> inventoryItemByName(String name) {
        return inventoryRepository.findInventoryItemByName(name);
    }

    public void updateInventoryItemQuantity(InventoryItem item) {
        inventoryRepository.save(item);
    }
}
