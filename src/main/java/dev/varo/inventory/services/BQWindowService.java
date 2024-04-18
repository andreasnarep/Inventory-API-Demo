package dev.varo.inventory.services;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.BQWindow;
import dev.varo.inventory.repositories.BQWindowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BQWindowService {
    @Autowired
    private BQWindowRepository bqWindowRepository;

    public List<BQWindow> allBQWindows() {
        return bqWindowRepository.findAll();
    }

    public Optional<BQWindow> bqWindowByName(String name) {
        return bqWindowRepository.findBQWindowByName(name);
    }
}
