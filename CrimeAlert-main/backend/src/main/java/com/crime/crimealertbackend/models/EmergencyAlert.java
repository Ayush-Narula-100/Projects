package com.crime.crimealertbackend.models;

import java.time.LocalDateTime;

class EmergencyAlert {
    private String alertId;
    private String userId; // ID of the person who triggered it
    private String location;
    private LocalDateTime timestamp;

    public EmergencyAlert(String alertId, String userId, String location) {
        this.alertId = alertId;
        this.userId = userId;
        this.location = location;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getAlertId() { return alertId; }
    public String getUserId() { return userId; }
    public String getLocation() { return location; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
