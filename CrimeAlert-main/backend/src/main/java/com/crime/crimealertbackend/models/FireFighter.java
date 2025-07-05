package com.crime.crimealertbackend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FireFighter extends User {

    public FireFighter(@JsonProperty("userId") String userId,
                       @JsonProperty("name") String name,
                       @JsonProperty("password") String password,
                       @JsonProperty("phoneNo") String phoneNo,
                       @JsonProperty("idNo") String idNo,
                       @JsonProperty("dob") String dob,
                       @JsonProperty("fcmToken") String fcmToken,
                       @JsonProperty("city") String city,
                       @JsonProperty("state") String state,
                       @JsonProperty("longitude") double longitude,
                       @JsonProperty("latitude") double latitude) {
        super(userId, name, password, "FireFighter",phoneNo,dob,idNo,fcmToken,city,state,longitude,latitude);
    }

    @Override
    public String getRole() {
        return "FireFighter";
    }
}
