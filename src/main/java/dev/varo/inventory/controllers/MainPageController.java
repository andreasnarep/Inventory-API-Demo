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

    @GetMapping("/bq-doors")
    public ResponseEntity<List<CompletedBQDoor>> getAllBQDoors() {
        return new ResponseEntity<>(mainPageService.allCompletedBQDoors(), HttpStatus.OK);
    }

    @GetMapping("/bq-doors/{month}")
    public ResponseEntity<Optional<List<CompletedBQDoor>>> getCompletedBQDoorsByMonth(@PathVariable String month) {
        return new ResponseEntity<>(mainPageService.completedBQDoorsByMonth(month), HttpStatus.OK);
    }

    @GetMapping("/bq-windows/{month}")
    public ResponseEntity<Optional<List<CompletedBQWindow>>> getCompletedBQWindowsByMonth(@PathVariable String month) {
        return new ResponseEntity<>(mainPageService.completedBQWindowsByMonth(month), HttpStatus.OK);
    }

    @GetMapping("/polo-doors/{month}")
    public ResponseEntity<Optional<List<CompletedPoloDoor>>> getCompletedPoloDoorsByMonth(@PathVariable String month) {
        return new ResponseEntity<>(mainPageService.completedPoloDoorsByMonth(month), HttpStatus.OK);
    }
}
