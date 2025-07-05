package com.crime.crimealertbackend.services;

import com.crime.crimealertbackend.utils.S3FileUtil;
import com.crime.crimealertbackend.utils.LambdaOtpSender;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AadhaarVerificationService {

    private static final String BUCKET_NAME = "backendcrimealertbucketfordata";
    private static final String AADHAAR_FILE_KEY = "citizen/aadhaar_data.json";
    private static final String CITIZEN_DETAILS_KEY = "UserLogin/citizen/Details.json";

    private final ObjectMapper mapper = new ObjectMapper();

    public JsonNode verifyAadhaar(String aadhaarNumber) throws Exception {
        String aadhaarJson = S3FileUtil.readFileFromS3(BUCKET_NAME, AADHAAR_FILE_KEY);
        JsonNode rootNode = mapper.readTree(aadhaarJson);

        if (!rootNode.isArray()) {
            throw new Exception("Invalid data format. Expected an array.");
        }

        for (JsonNode userNode : rootNode) {
            if (userNode.has("aadhaarNumber") &&
                    userNode.get("aadhaarNumber").asText().equals(aadhaarNumber)) {

                if (isAlreadyRegistered(aadhaarNumber)) {
                    ((ObjectNode) userNode).put("message", "Account already exists");
                } else {
                    // ✅ Not registered: generate OTP and send
                    String phoneNumber = userNode.get("phoneNo").asText();
                    String otp = generateOtp();

                    boolean sent = LambdaOtpSender.sendOtp(otp,"+91"+phoneNumber);

                    if (sent) {
                        ((ObjectNode) userNode).put("otp", otp);
                        ((ObjectNode) userNode).put("message", "OTP sent successfully");
                    } else {
                        ((ObjectNode) userNode).put("message", "Failed to send OTP");
                    }
                }

                return userNode;
            }
        }

        return null; // Aadhaar not found
    }

    private boolean isAlreadyRegistered(String aadhaarNumber) throws Exception {
        String citizenJson = S3FileUtil.readFileFromS3(BUCKET_NAME, CITIZEN_DETAILS_KEY);
        JsonNode rootNode = mapper.readTree(citizenJson);

        if (!rootNode.isArray()) {
            throw new Exception("Invalid citizen details format.");
        }

        for (JsonNode userNode : rootNode) {
            if (userNode.has("idNo") && userNode.get("idNo").asText().equals(aadhaarNumber)) {
                return true;
            }
        }

        return false;
    }

    private String generateOtp() {
        int otp = new Random().nextInt(900000) + 100000; // 6-digit OTP
        return String.valueOf(otp);
    }
}
