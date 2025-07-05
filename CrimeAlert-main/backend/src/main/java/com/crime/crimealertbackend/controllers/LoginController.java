package com.crime.crimealertbackend.controllers;

import com.crime.crimealertbackend.models.ApiResponse;
import com.crime.crimealertbackend.models.LoginRequest;
import com.crime.crimealertbackend.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@RequestBody LoginRequest request) {
        try {
            Map<String, Object> userData = loginService.login(request);
            return ResponseEntity.ok(new ApiResponse(true, "Login successful", userData));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(false, "Login failed: " + e.getMessage())
            );
        }
    }
    @PostMapping("/update-fcm-token")
    public ResponseEntity<ApiResponse> updateFcmToken(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        String token = body.get("token");

        try {
            loginService.updateFcmToken(userId, token);
            return ResponseEntity.ok(new ApiResponse(true, "FCM token updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ApiResponse(false, "Failed to update token: " + e.getMessage())
            );
        }
    }

}
