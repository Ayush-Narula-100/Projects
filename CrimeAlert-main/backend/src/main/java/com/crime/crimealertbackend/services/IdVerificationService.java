package com.crime.crimealertbackend.services;

import com.crime.crimealertbackend.utils.S3FileUtil;
import com.crime.crimealertbackend.utils.LambdaOtpSender;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class IdVerificationService {

    private static final String BUCKET_NAME = "backendcrimealertbucketfordata";
    private static final String ID_FILE_KEY = "authority/id_data.json";
    private static final String AUTHORITY_DETAILS_KEY = "UserLogin/police/Details.json";

    private final ObjectMapper mapper = new ObjectMapper();

    public JsonNode verifyId(String idNo) throws Exception {
        // Read ID data from S3
        String idDataJson = S3FileUtil.readFileFromS3(BUCKET_NAME, ID_FILE_KEY);
        JsonNode rootNode = mapper.readTree(idDataJson);

        if (!rootNode.isArray()) {
            throw new Exception("Invalid data format. Expected an array.");
        }

        for (JsonNode userNode : rootNode) {
            if (userNode.has("idNo") && userNode.get("idNo").asText().equals(idNo)) {

                if (isAlreadyRegistered(idNo)) {
                    ((ObjectNode) userNode).put("message", "Account already exists");
                } else {
                    // Not registered: generate OTP and send
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

                return userNode; // Return result to controller
            }
        }

        return null; // ID not found
    }

    private boolean isAlreadyRegistered(String idNo) throws Exception {
        String authorityDetailsJson = S3FileUtil.readFileFromS3(BUCKET_NAME, AUTHORITY_DETAILS_KEY);
        JsonNode rootNode = mapper.readTree(authorityDetailsJson);

        if (!rootNode.isArray()) {
            throw new Exception("Invalid authority details format.");
        }

        for (JsonNode userNode : rootNode) {
            if (userNode.has("idNo") && userNode.get("idNo").asText().equals(idNo)) {
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
