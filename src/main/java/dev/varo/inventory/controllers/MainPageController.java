package dev.varo.inventory.controllers;

import dev.varo.inventory.objects.CompletedBQDoor;
import dev.varo.inventory.objects.CompletedBQWindow;
import dev.varo.inventory.objects.CompletedPoloDoor;
import dev.varo.inventory.objects.InventoryItem;
import dev.varo.inventory.services.MainPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/main-page")
@CrossOrigin(origins = "*")
public class MainPageController {
    @Autowired
    private MainPageService mainPageService;

    @GetMapping("/bq-doors/{month}")
    public ResponseEntity<Optional<List<CompletedBQDoor>>> getCompletedBQDoorsByMonth(@PathVariable String month) {
        Optional<List<CompletedBQDoor>> completedBQDoors = mainPageService.completedBQDoorsByMonth(month);

        if (completedBQDoors.isPresent()) {
            if (completedBQDoors.get().isEmpty()) {
                return new ResponseEntity<>(completedBQDoors, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(completedBQDoors, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(completedBQDoors, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/bq-windows/{month}")
    public ResponseEntity<Optional<List<CompletedBQWindow>>> getCompletedBQWindowsByMonth(@PathVariable String month) {
        Optional<List<CompletedBQWindow>> completedBQWindows = mainPageService.completedBQWindowsByMonth(month);

        if (completedBQWindows.isPresent()) {
            if (completedBQWindows.get().isEmpty()) {
                return new ResponseEntity<>(completedBQWindows, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(completedBQWindows, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(completedBQWindows, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/polo-doors/{month}")
    public ResponseEntity<Optional<List<CompletedPoloDoor>>> getCompletedPoloDoorsByMonth(@PathVariable String month) {
        Optional<List<CompletedPoloDoor>> completedPoloDoors = mainPageService.completedPoloDoorsByMonth(month);

        if (completedPoloDoors.isPresent()) {
            if (completedPoloDoors.get().isEmpty()) {
                return new ResponseEntity<>(completedPoloDoors, HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(completedPoloDoors, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(completedPoloDoors, HttpStatus.NOT_FOUND);
        }
    }
}
