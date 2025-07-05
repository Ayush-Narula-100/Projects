package com.crime.crimealertbackend.services;

import com.crime.crimealertbackend.models.*;
import com.crime.crimealertbackend.utils.S3FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;
import com.google.firebase.messaging.*;


import java.util.*;

@Service
public class EmergencyService {

    private static final String BUCKET_NAME = "backendcrimealertbucketfordata";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> handleEmergency(EmergencyRequest request) throws Exception {
        List<User> allAuthorities = new ArrayList<>();

        // Load all authorities from S3
        String[] roles = {"Police"};
        for (String role : roles) {
            String fileKey = "UserLogin/" + role.toLowerCase() + "/Details.json";
            if (S3FileUtil.fileExistsInS3(BUCKET_NAME, fileKey)) {
                String json = S3FileUtil.readFileFromS3(BUCKET_NAME, fileKey);
                if (json != null && !json.trim().isEmpty()) {
                    List<User> roleUsers = objectMapper.readValue(json, new TypeReference<List<User>>() {});
                    allAuthorities.addAll(roleUsers);
                }
            }
        }

        String reportId = UUID.randomUUID().toString(); // Generate a unique ID
        Report report = new Report(
                reportId,
                request.getCitizenId(),
                request.getDescription(),
                request.getLocation()
        );

        // Filter by distance (within 3 km)
        List<User> nearbyAuthorities = new ArrayList<>();
        for (User user : allAuthorities) {
            double dist = distance(request.getLatitude(), request.getLongitude(),
                    user.getLatitude(), user.getLongitude());
            if (dist <= 10.0) {
                nearbyAuthorities.add(user);
                sendNotificationToToken(user.getFcmToken(), "🚨 Emergency!", request.getName() + " needs help nearby.");

                // Add the report to the user's reports list
                user.getReports().add(report);
            }
        }

        // After modifying the reports, update the S3 file
        for (String role : roles) {
            String fileKey = "UserLogin/" + role.toLowerCase() + "/Details.json";
            if (S3FileUtil.fileExistsInS3(BUCKET_NAME, fileKey)) {
                // Convert the updated list of users to JSON
                String updatedJson = objectMapper.writeValueAsString(allAuthorities);
                // Upload the updated list back to S3
                S3FileUtil.writeFileToS3(BUCKET_NAME, fileKey, updatedJson);
            }
        }

        return nearbyAuthorities;
    }


    public void sendNotificationToToken(String token, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            System.out.println("kyu nhi hua:"+e);
        }
    }

    // Haversine formula
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of Earth in KM
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
