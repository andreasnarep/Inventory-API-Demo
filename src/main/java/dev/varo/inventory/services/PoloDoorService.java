package dev.varo.inventory.services;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.PoloDoor;
import dev.varo.inventory.repositories.PoloDoorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PoloDoorService {
    @Autowired
    private PoloDoorRepository poloDoorRepository;

    public List<PoloDoor> allPoloDoors() {
        return poloDoorRepository.findAll();
    }

    public Optional<PoloDoor> poloDoorByName(String name) {
        return poloDoorRepository.findPoloDoorByName(name);
    }
}
