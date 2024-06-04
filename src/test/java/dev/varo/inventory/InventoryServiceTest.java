package dev.varo.inventory;


import dev.varo.inventory.objects.InventoryItem;
import dev.varo.inventory.repositories.InventoryRepository;
import dev.varo.inventory.services.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
    @Mock
    private InventoryRepository inventoryRepository;
    @InjectMocks
    private InventoryService inventoryService;

    @Test
    public void getAllInventoryItems() {
        String name = "605x1537";
        String slat = "5mm";
        int quantity = 10;
        int lowerLimit = 7;

        String name2 = "asd";
        String slat2 = "5mm";
        int quantity2 = 26;
        int lowerLimit2 = 10;

        InventoryItem item = new InventoryItem(name, slat, quantity, lowerLimit);
        InventoryItem item2 = new InventoryItem(name2, slat2, quantity2, lowerLimit2);

        when(inventoryRepository.findAll()).thenReturn(List.of(item, item2));

        List<InventoryItem> result = inventoryService.allInventoryItems();

        verify(inventoryRepository, times(1)).findAll();
        assertEquals(item, result.get(0));
        assertEquals(item2, result.get(1));
    }

    @Test
    public void getInventoryItemByName() {
        String name = "605x1537";
        String slat = "5mm";
        int quantity = 10;
        int lowerLimit = 7;

        InventoryItem item = new InventoryItem(name, slat, quantity, lowerLimit);

        when(inventoryRepository.findInventoryItemByName(name)).thenReturn(Optional.of(item));

        Optional<InventoryItem> result = inventoryService.inventoryItemByName(name);

        verify(inventoryRepository, times(1)).findInventoryItemByName(name);
        assertTrue(result.isPresent());
        assertEquals(item, result.get());
    }

    @Test
    public void updateInventoryItemQuantity() {
        String name = "605x1537";
        String slat = "5mm";
        int quantity = 10;
        int lowerLimit = 7;

        InventoryItem item = new InventoryItem(name, slat, quantity, lowerLimit);

        when(inventoryRepository.findInventoryItemByName(name)).thenReturn(Optional.of(item));

        Optional<InventoryItem> result = inventoryService.inventoryItemByName(name);

        assertTrue(result.isPresent());
        assertEquals(item, result.get());

        item.setQuantity(item.getQuantity() + 5);
        inventoryService.updateInventoryItem(item);
        result = inventoryService.inventoryItemByName(name);

        verify(inventoryRepository, times(1)).save(item);
        assertTrue(result.isPresent());
        assertEquals(item, result.get());
    }
}
