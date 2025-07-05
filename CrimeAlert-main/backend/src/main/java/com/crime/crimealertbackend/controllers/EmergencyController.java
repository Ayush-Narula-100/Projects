package com.crime.crimealertbackend.controllers;

import com.crime.crimealertbackend.models.*;
import com.crime.crimealertbackend.services.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    @PostMapping("/emergency")
    public ResponseEntity<?> handleEmergency(@RequestBody EmergencyRequest request) {
        try {
            List<User> nearbyAuthorities = emergencyService.handleEmergency(request);
            return ResponseEntity.ok(nearbyAuthorities);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to handle emergency: " + e.getMessage());
        }
    }
}
