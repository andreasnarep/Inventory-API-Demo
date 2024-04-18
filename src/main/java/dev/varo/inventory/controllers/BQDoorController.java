package dev.varo.inventory.controllers;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.services.BQDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bq-doors")
@CrossOrigin(origins = "*")
public class BQDoorController {
    @Autowired
    private BQDoorService bqDoorService;

    @GetMapping
    public ResponseEntity<List<BQDoor>> getAllBQDoors() {
        return new ResponseEntity<>(bqDoorService.allBQDoors(), HttpStatus.OK);
    }
}
