package com.crime.crimealertbackend.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Report {
    private String reportId;
    private String citizenId; // ID of the person who reported
    private String description;
    private String location;
    private long timestamp;
    private String status; // Pending, In Progress, Resolved

    // Default constructor
    public Report() {}

    @JsonCreator
    public Report(
            @JsonProperty("reportId") String reportId,
            @JsonProperty("citizenId") String citizenId,
            @JsonProperty("description") String description,
            @JsonProperty("location") String location
    ) {
        this.reportId = reportId;
        this.citizenId = citizenId;
        this.description = description;
        this.location = location;
        this.timestamp = System.currentTimeMillis();  // Set timestamp to current time in milliseconds
        this.status = "Pending";  // Default status
    }

    // Getters and setters for the fields
    public String getReportId() { return reportId; }
    public String getCitizenId() { return citizenId; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public long getTimestamp() { return timestamp; }
    public String getStatus() { return status; }

    // Update status
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // Display report for debugging purposes
    public void displayReport() {
        System.out.println("Report ID: " + reportId);
        System.out.println("Citizen ID: " + citizenId);
        System.out.println("Description: " + description);
        System.out.println("Location: " + location);
        System.out.println("Timestamp: " + timestamp);
        System.out.println("Status: " + status);
    }
}
