package com.crime.crimealertbackend.services;

import com.crime.crimealertbackend.models.*;
import com.crime.crimealertbackend.utils.S3FileUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RegistrationService {

    private static final String BUCKET_NAME = "backendcrimealertbucketfordata"; // TODO: Replace with your bucket name
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void register(RegistrationRequest request) throws Exception {
        String roleLower = request.getRole().toLowerCase();
        String fileKey = "UserLogin/" + roleLower + "/Details.json";

        List<User> users = new ArrayList<>();

        // ✅ Load existing users from S3
        if (S3FileUtil.fileExistsInS3(BUCKET_NAME, fileKey)) {
            String existingJson = S3FileUtil.readFileFromS3(BUCKET_NAME, fileKey);

            if (existingJson != null && !existingJson.trim().isEmpty()) {
                users = objectMapper.readValue(existingJson, new TypeReference<List<User>>() {});
            }

            // ✅ Check for duplicate userId
            for (User existingUser : users) {
                if (existingUser.getUserId().equals(request.getUserId())) {
                    throw new Exception("Username already taken");
                }
            }
        }

        // ✅ Create new user object
        User user;
        switch (request.getRole()) {
            case "Citizen":
                user = new Citizen(request.getUserId(), request.getName(), request.getPassword(), request.getIdNo(), request.getPhoneNo(), request.getDob(),request.getFcmToken(),request.getCity(),request.getState(),request.getLongitude(),request.getLatitude());
                break;
            case "Police":
                user = new Police(request.getUserId(), request.getName(), request.getPassword(), request.getPhoneNo(), request.getIdNo(), request.getDob(),request.getFcmToken(),request.getCity(),request.getState(),request.getLongitude(),request.getLatitude());
                break;
            case "Medical":
                user = new Medical(request.getUserId(), request.getName(), request.getPassword(), request.getPhoneNo(), request.getIdNo(), request.getDob(),request.getFcmToken(),request.getCity(),request.getState(),request.getLongitude(),request.getLatitude());
                break;
            case "FireFighter":
                user = new FireFighter(request.getUserId(), request.getName(), request.getPassword(), request.getPhoneNo(), request.getIdNo(), request.getDob(),request.getFcmToken(),request.getCity(),request.getState(),request.getLongitude(),request.getLatitude());
                break;
            case "Admin":
                user = new Admin(request.getUserId(), request.getName(), request.getPassword(), request.getPhoneNo(), request.getIdNo(), request.getDob(),request.getFcmToken(),request.getCity(),request.getState(),request.getLongitude(),request.getLatitude());
                break;
            default:
                throw new Exception("Unknown role: " + request.getRole());
        }

        // ✅ Add new user and save updated list to S3
        users.add(user);
        String updatedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
        S3FileUtil.writeFileToS3(BUCKET_NAME, fileKey, updatedJson);
    }
}
