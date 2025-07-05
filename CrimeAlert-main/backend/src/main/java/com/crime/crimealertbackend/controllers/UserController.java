package com.crime.crimealertbackend.controllers;

import com.crime.crimealertbackend.models.ApiResponse;
import com.crime.crimealertbackend.models.RegistrationRequest;
import com.crime.crimealertbackend.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegistrationRequest request) {
        try {
            registrationService.register(request);
            return ResponseEntity.ok(new ApiResponse(true, "User registered successfully."));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(false, "Registration failed: " + e.getMessage())
            );
        }
    }
}
