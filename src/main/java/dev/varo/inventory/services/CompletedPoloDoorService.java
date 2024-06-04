package dev.varo.inventory.services;

import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.CompletedPoloDoor;
import dev.varo.inventory.repositories.CompletedPoloDoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedPoloDoorService {
    @Autowired
    private CompletedPoloDoorRepository completedPoloDoorRepository;

    public void addCompletedPoloDoors(List<CompletedPoloDoor> completedPoloDoors) {
        completedPoloDoorRepository.saveAll(completedPoloDoors);
    }

}
