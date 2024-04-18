package dev.varo.inventory.controllers;

import dev.varo.inventory.objects.PoloDoor;
import dev.varo.inventory.services.PoloDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/polo-doors")
@CrossOrigin(origins = "*")
public class PoloDoorController {
    @Autowired
    private PoloDoorService poloDoorService;

    @GetMapping
    public ResponseEntity<List<PoloDoor>> getAllPoloDoors() {
        return new ResponseEntity<>(poloDoorService.allPoloDoors(), HttpStatus.OK);
    }
}
