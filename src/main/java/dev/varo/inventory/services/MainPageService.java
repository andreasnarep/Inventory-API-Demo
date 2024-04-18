package dev.varo.inventory.services;

import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.CompletedBQWindow;
import dev.varo.inventory.objects.CompletedPoloDoor;
import dev.varo.inventory.objects.InventoryItem;
import dev.varo.inventory.repositories.CompletedBQDoorRepository;
import dev.varo.inventory.repositories.CompletedBQWindowRepository;
import dev.varo.inventory.repositories.CompletedPoloDoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MainPageService {
    @Autowired
    private CompletedBQDoorRepository completedBQDoorRepository;
    @Autowired
    private CompletedBQWindowRepository completedBQWindowRepository;
    @Autowired
    private CompletedPoloDoorRepository completedPoloDoorRepository;

    public List<CompletedBQDoor> allCompletedBQDoors() {
        return completedBQDoorRepository.findAll();
    }

    public Optional<List<CompletedBQDoor>> completedBQDoorsByMonth(String month) {
        return completedBQDoorRepository.findCompletedBQDoorsByMonth(month);
    }

    public Optional<List<CompletedBQWindow>> completedBQWindowsByMonth(String month) {
        return completedBQWindowRepository.findCompletedBQWindowsByMonth(month);
    }

    public Optional<List<CompletedPoloDoor>> completedPoloDoorsByMonth(String month) {
        return completedPoloDoorRepository.findCompletedPoloDoorsByMonth(month);
    }
}
