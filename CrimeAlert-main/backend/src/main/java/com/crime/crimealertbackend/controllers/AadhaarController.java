package com.crime.crimealertbackend.controllers;

import com.crime.crimealertbackend.models.AadhaarVerificationRequest;
import com.crime.crimealertbackend.models.ApiResponse;
import com.crime.crimealertbackend.services.AadhaarVerificationService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AadhaarController {

    @Autowired
    private AadhaarVerificationService aadhaarVerificationService;

    @PostMapping("/verify-aadhaar")
    public ResponseEntity<ApiResponse> verifyAadhaar(@RequestBody AadhaarVerificationRequest request) {
        try {
            JsonNode user = aadhaarVerificationService.verifyAadhaar(request.getAadhaarNumber());

            if (user != null) {
                // Check if the user has the "message" field saying already exists
                if (user.has("message") && "Account already exists".equals(user.get("message").asText())) {
                    return ResponseEntity.ok(new ApiResponse(false, "Account already exists", user));
                }

                return ResponseEntity.ok(new ApiResponse(true, "Aadhaar found", user));
            } else {
                return ResponseEntity.status(404).body(new ApiResponse(false, "Aadhaar not found"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }

}
