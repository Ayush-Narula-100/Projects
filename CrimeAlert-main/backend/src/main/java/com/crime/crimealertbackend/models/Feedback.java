package com.crime.crimealertbackend.models;

import java.time.LocalDateTime;

class Feedback {
    private String feedbackId;
    private String userId; // ID of citizen or authority
    private String message;
    private int rating; // 1 to 5
    private LocalDateTime timestamp;

    public Feedback(String feedbackId, String userId, String message, int rating) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.message = message;
        this.rating = rating;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public String getFeedbackId() { return feedbackId; }
    public String getUserId() { return userId; }
    public String getMessage() { return message; }
    public int getRating() { return rating; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
