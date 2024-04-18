package dev.varo.inventory.controllers;

import dev.varo.inventory.objects.BQDoor;
import dev.varo.inventory.objects.BQWindow;
import dev.varo.inventory.services.BQWindowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bq-windows")
@CrossOrigin(origins = "*")
public class BQWindowController {
    @Autowired
    private BQWindowService bqWindowService;

    @GetMapping
    public ResponseEntity<List<BQWindow>> getAllBQWindows() {
        return new ResponseEntity<>(bqWindowService.allBQWindows(), HttpStatus.OK);
    }
}
