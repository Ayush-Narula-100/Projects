package com.crime.crimealertbackend.models;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.ArrayList;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "role",// This key in JSON will determine the subclass
        visible = true,
        include = JsonTypeInfo.As.PROPERTY
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Citizen.class, name = "Citizen"),
        @JsonSubTypes.Type(value = Police.class, name = "Police"),
        @JsonSubTypes.Type(value = Medical.class, name = "Medical"),
        @JsonSubTypes.Type(value = FireFighter.class, name = "FireFighter"),
        @JsonSubTypes.Type(value = Admin.class, name = "Admin")
})
public abstract class User {
    private String userId;
    private String name;
    private String password;
    private String role;
    private String phoneNo;
    private String dob;
    private String idNo;
    private String fcmToken;
    private String city;
    private String state;
    private double longitude;
    private double latitude;
    private List<Report> reports;

    public User() {}
    public User(String userId, String name, String password,String role,String phoneNo,String dob,String idNo,String fcmToken,String city,String state,double longitude,double latitude) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.phoneNo=phoneNo;
        this.dob=dob;
        this.idNo=idNo;
        this.fcmToken=fcmToken;
        this.latitude=latitude;
        this.city=city;
        this.longitude=longitude;
        this.state=state;
        this.reports = new ArrayList<>(); ;
    }

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getPassword() {
        return password;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public String getDob() {
        return dob;
    }
    public String getIdNo() {
        return idNo;
    }
    public String getFcmToken() {
        return fcmToken;
    }
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
    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }


    protected void setPassword(String password) {
        this.password = password;
    }

    public void displayUser() {
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + name);
    }


    public String getRole(){
        return role;
    }
}
