package com.example.appservice.controller;

import com.example.appservice.model.AppOwner;
import com.example.appservice.service.AppOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class AppOwnerController {

    private final AppOwnerService appOwnerService;

    public AppOwnerController(AppOwnerService appOwnerService) {
        this.appOwnerService = appOwnerService;
    }

    // Add new owner
    @PostMapping("/add")
    public ResponseEntity<AppOwner> addOwner(@RequestBody AppOwner owner) {
        AppOwner savedOwner = appOwnerService.saveOwner(owner);
        return ResponseEntity.ok(savedOwner);
    }

    // Optional: List all owners
    @GetMapping("/all")
    public ResponseEntity<List<AppOwner>> getAllOwners() {
        return ResponseEntity.ok(appOwnerService.getAllOwners());
    }
}
