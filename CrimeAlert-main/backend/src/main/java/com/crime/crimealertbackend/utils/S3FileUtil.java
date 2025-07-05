package com.crime.crimealertbackend.utils;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.nio.charset.StandardCharsets;

public class S3FileUtil {

    private static final S3Client s3 = S3Client.builder()
            .region(Region.AP_SOUTH_1) // Update to your AWS region
            .credentialsProvider(DefaultCredentialsProvider.create())
            .build();

    // READ file content from S3 as string
    public static String readFileFromS3(String bucketName, String key) throws Exception {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes = s3.getObjectAsBytes(getObjectRequest);
        return objectBytes.asString(StandardCharsets.UTF_8);
    }

    // WRITE content to S3 as file
    public static void writeFileToS3(String bucketName, String key, String content) throws Exception {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("application/json")
                .build();

        s3.putObject(putRequest, RequestBody.fromString(content));
    }

    // CHECK if a file exists in S3
    public static boolean fileExistsInS3(String bucketName, String key) {
        try {
            HeadObjectRequest headRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3.headObject(headRequest);
            return true;
        } catch (S3Exception e) {
            return false;
        }
    }

}
