package com.crime.crimealertbackend.models;

public class RegistrationRequest {
    private String role;
    private String userId;
    private String name;
    private String password;
    private String idNo;
    private String dob;
    private String phoneNo;
    private String fcmToken;
    private String city;
    private String state;
    private double longitude;
    private double latitude;

    public String getRole() { return role; }
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getIdNo() { return idNo; }
    public String getDob() { return dob; }
    public String getPhoneNo() { return phoneNo; }
    public String getFcmToken() { return fcmToken; }
    public String getCity() {
        return city;
    }
    public String getState() {
        return state;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
}
