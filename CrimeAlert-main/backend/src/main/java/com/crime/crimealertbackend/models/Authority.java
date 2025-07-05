package com.crime.crimealertbackend.models;

abstract class Authority extends User{
    //private String location;

    public Authority(String userId, String name, String password, String role,String phoneNo,String dob,String idNo,String fcmToken,String city,String state,double longitude,double latitude) {
        super(userId, name, password,role,phoneNo,dob,idNo,fcmToken,city,state, longitude,latitude);
        //this.location = location;
    }

    //public String getBadgeNumber() { return location; }

    @Override
    public String getRole() {
        return "Authority";
    }
    public abstract String getDepartment();
}
