package dev.varo.inventory.services;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.InventoryItem;
import dev.varo.inventory.repositories.BQDoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BQDoorService {
    @Autowired
    private BQDoorRepository bqDoorRepository;

    public List<BQDoor> allBQDoors() {
        return bqDoorRepository.findAll();
    }

    public Optional<BQDoor> bqDoorByName(String name) {
        return bqDoorRepository.findBQDoorByName(name);
    }
}
