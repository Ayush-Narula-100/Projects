package com.crime.crimealertbackend.services;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Aadhaar {
    @JsonProperty("aadhaarNumber")
    private String aadhaarNumber;
    @JsonProperty("name")
    private String name;
    @JsonProperty("phoneNo")
    private String phoneNo;
    @JsonProperty("dob")
    private String dob;
    public Aadhaar() {}
    Aadhaar(String aadhaarNumber,String name,String phoneNo,String dob){
        this.aadhaarNumber=aadhaarNumber;
        this.name=name;
        this.phoneNo=phoneNo;
        this.dob=dob;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }
    public String getName() {
        return name;
    }
    public String phoneNo() {
        return phoneNo;
    }
    public String getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return "Aadhaar{" +
                "aadhaarNumber='" + aadhaarNumber + '\'' +
                ", name='" + name + '\'' +
                ", phoneNo='"+phoneNo+'\'' +
                ", dob='" + dob + '\'' +
                '}';
    }

}

