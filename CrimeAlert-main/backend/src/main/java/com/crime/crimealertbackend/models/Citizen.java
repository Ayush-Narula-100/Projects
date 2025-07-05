package com.crime.crimealertbackend.models;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Citizen extends User {
    public Citizen() {
        super();// Needed by Jackson
    }
    public Citizen(@JsonProperty("userId") String userId,
                   @JsonProperty("name") String name,
                   @JsonProperty("password") String password,
                   @JsonProperty("idNo") String idNo,
                   @JsonProperty("phoneNo") String phoneNo,
                   @JsonProperty("dob") String dob,
                   @JsonProperty("fcmToken") String fcmToken,
                   @JsonProperty("city") String city,
                   @JsonProperty("state") String state,
                   @JsonProperty("longitude") double longitude,
                   @JsonProperty("latitude") double latitude
                   ) {
        super(userId, name, password , "Citizen",phoneNo,dob,idNo,fcmToken,city,state,longitude,latitude);
    }

    @Override
    public String getRole() {
        return "Citizen";
    }

}
