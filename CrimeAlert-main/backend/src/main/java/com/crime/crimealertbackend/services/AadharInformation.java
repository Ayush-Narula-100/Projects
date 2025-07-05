package com.crime.crimealertbackend.services;

import com.crime.crimealertbackend.services.Aadhaar;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.time.LocalDate;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AadharInformation {
    private static final String fileName = "Data/citizen/aadhaar_data.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static void saveAadhaar(String aadhaarNumber,String name,String phoneNo,String dob) {
        Aadhaar newAadhaar = new Aadhaar(aadhaarNumber,name,phoneNo, dob);
        List<Aadhaar> aadhaarList = fetchAllAadhaars(); // Get existing records

        // Check if Aadhaar number already exists
        for (Aadhaar aadhaar : aadhaarList) {
            if (aadhaar.getAadhaarNumber().equals(newAadhaar.getAadhaarNumber())) {
                System.out.println("Aadhaar number already exists. Skipping save.");
                return;
            }
        }

        aadhaarList.add(newAadhaar); // Add new record

        try {
            objectMapper.writeValue(new File(fileName), aadhaarList);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving Aadhaar: " + e.getMessage());
        }

    }

    public static Aadhaar fetchAadhaar(String aadhaarNumber) {
        List<Aadhaar> aadhaarList = fetchAllAadhaars();

        for (Aadhaar aadhaar : aadhaarList) {
            if (aadhaar.getAadhaarNumber().equals(aadhaarNumber)) {
                return aadhaar; // Found the matching record
            }
        }
        System.out.println("Aadhaar number not found.");
        return null;
    }

    public static List<Aadhaar> fetchAllAadhaars() {
        try {
            File file = new File(fileName);

            if (!file.exists()) {
                return new ArrayList<>(); // Return empty list if file does not exist
            }

            return objectMapper.readValue(file, new TypeReference<List<Aadhaar>>() {});
        } catch (IOException e) {
            System.err.println("Error reading Aadhaar data: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}