package dev.varo.inventory.services;

import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.CompletedBQWindow;
import dev.varo.inventory.repositories.CompletedBQWindowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompletedBQWindowService {
    @Autowired
    private CompletedBQWindowRepository completedBQWindowRepository;

    public void addCompletedBQWindow(CompletedBQWindow completedBQWindow) {
        completedBQWindowRepository.save(completedBQWindow);
    }

    public void addCompletedBQWindows(List<CompletedBQWindow> completedBQWindows) {
        completedBQWindowRepository.saveAll(completedBQWindows);
    }

}
