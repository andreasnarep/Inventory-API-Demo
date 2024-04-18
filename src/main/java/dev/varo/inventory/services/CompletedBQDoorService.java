package dev.varo.inventory.services;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.repositories.BQDoorRepository;
import dev.varo.inventory.repositories.CompletedBQDoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedBQDoorService {
    @Autowired
    private CompletedBQDoorRepository completedBQDoorRepository;

    public void addCompletedBQDoor(CompletedBQDoor completedBQDoor) {
        completedBQDoorRepository.save(completedBQDoor);
    }

    public void addCompletedBQDoors(List<CompletedBQDoor> completedBQDoors) {
        completedBQDoorRepository.saveAll(completedBQDoors);
    }
}
