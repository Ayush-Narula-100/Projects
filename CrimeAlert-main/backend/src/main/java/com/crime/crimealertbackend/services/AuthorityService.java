package com.crime.crimealertbackend.services;

import com.crime.crimealertbackend.models.Report;
import com.crime.crimealertbackend.models.User;
import com.crime.crimealertbackend.utils.S3FileUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityService {

    private static final String BUCKET_NAME = "backendcrimealertbucketfordata";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Report> getReportsByAuthorityId(String authorityId) throws Exception {
        String fileKey = "UserLogin/police/Details.json";

        if (!S3FileUtil.fileExistsInS3(BUCKET_NAME, fileKey)) {
            System.out.println("not bucket");
            return new ArrayList<>();
        }

        String json = S3FileUtil.readFileFromS3(BUCKET_NAME, fileKey);
        List<User> users = objectMapper.readValue(json, new TypeReference<List<User>>() {});
        for (User user : users) {
            System.out.println("user"+authorityId+"=="+user.getIdNo());
            if (user.getIdNo().equals(authorityId)) {
                System.out.println("got"+authorityId+"=="+user.getIdNo());
                System.out.println("report"+user.getReports());
                return user.getReports() != null ? user.getReports() : new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }
}
