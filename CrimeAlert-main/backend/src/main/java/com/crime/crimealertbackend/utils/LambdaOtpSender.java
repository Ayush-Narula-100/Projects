package com.crime.crimealertbackend.utils;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

public class LambdaOtpSender {

    private static final String FUNCTION_NAME = "sns-otp-sender"; // Lambda function name
    private static final Region REGION = Region.AP_SOUTH_1; // AWS region

    public static boolean sendOtp(String otp, String phoneNumber) {
        LambdaClient lambdaClient = LambdaClient.builder()
                .region(REGION)
                .build();

        try {
            String jsonPayload = String.format("{\"otp\": \"%s\", \"phoneNumber\": \"%s\"}", otp, phoneNumber);

            InvokeRequest request = InvokeRequest.builder()
                    .functionName(FUNCTION_NAME)
                    .payload(SdkBytes.fromUtf8String(jsonPayload))
                    .build();

            InvokeResponse response = lambdaClient.invoke(request);

            String responseJson = response.payload().asUtf8String();
            System.out.println("Lambda Response: " + responseJson);
            System.out.println("Status Code: " + response.statusCode());

            return response.statusCode() == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            lambdaClient.close();
        }
    }
}
