package com.crime.crimealertbackend.services;

import com.crime.crimealertbackend.models.LoginRequest;
import com.crime.crimealertbackend.utils.S3FileUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    private static final String BUCKET_NAME = "backendcrimealertbucketfordata"; // TODO: Replace with your bucket name
    private static final String CITIZEN_FILE_KEY = "UserLogin/citizen/Details.json";
    private static final String AUTHORITY_FILE_KEY = "UserLogin/police/Details.json";

    private final ObjectMapper mapper = new ObjectMapper();

    public void updateFcmToken(String userId, String fcmToken) throws Exception {
        JsonNode userNode = findUserByUserId(userId);
        if (userNode == null) {
            throw new Exception("User not found.");
        }

        String role = userNode.get("role").asText();
        String fileKey = role.equalsIgnoreCase("citizen") ? CITIZEN_FILE_KEY : AUTHORITY_FILE_KEY;
        String json = S3FileUtil.readFileFromS3(BUCKET_NAME, fileKey);
        JsonNode root = mapper.readTree(json);

        boolean updated = false;
        for (JsonNode user : root) {
            if (user.get("userId").asText().equals(userId)) {
                ((com.fasterxml.jackson.databind.node.ObjectNode) user).put("fcmToken", fcmToken);
                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new Exception("User not found in JSON file.");
        }

        // Write back updated file to S3
        S3FileUtil.writeFileToS3(BUCKET_NAME, fileKey, mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root));
    }


    public Map<String, Object> login(LoginRequest request) throws Exception {
        JsonNode user = findUserByUserId(request.getUserId());

        if (user == null) {
            throw new Exception("User ID not found.");
        }

        if (!user.get("password").asText().equals(request.getPassword())) {
            throw new Exception("Incorrect password.");
        }

        // Return full user details
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", user.get("userId").asText());
        userData.put("name", user.get("name").asText());
        userData.put("phoneNo", user.get("phoneNo").asText());
        userData.put("idNo", user.get("idNo").asText());  // Aadhaar
        userData.put("dob", user.get("dob").asText());
        userData.put("state", user.get("state").asText());
        userData.put("city", user.get("city").asText());
        userData.put("latitude", user.get("latitude").asDouble());
        userData.put("role", user.get("role").asText());
        userData.put("longitude", user.get("longitude").asDouble());

        return userData;
    }

    private JsonNode findUserByUserId(String userId) throws Exception {
        // Read and parse the citizen data from S3
        String citizenJson = S3FileUtil.readFileFromS3(BUCKET_NAME, CITIZEN_FILE_KEY);
        JsonNode citizenUsers = mapper.readTree(citizenJson);
        for (JsonNode user : citizenUsers) {
            if (user.get("userId").asText().equals(userId)) {
                return user;
            }
        }

        // Read and parse the police/authority data from S3
        String authorityJson = S3FileUtil.readFileFromS3(BUCKET_NAME, AUTHORITY_FILE_KEY);
        JsonNode authorityUsers = mapper.readTree(authorityJson);
        for (JsonNode user : authorityUsers) {
            if (user.get("userId").asText().equals(userId)) {
                return user;
            }
        }

        return null;
    }
}
