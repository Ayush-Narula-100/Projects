package com.crime.crimealertbackend.controllers;

import com.crime.crimealertbackend.models.Report;
import com.crime.crimealertbackend.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authorityReports")
@CrossOrigin(origins = "*") // Enable CORS for frontend
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/{authorityId}")
    public List<Report> getReports(@PathVariable("authorityId") String authorityId)
    {
        try {
            return authorityService.getReportsByAuthorityId(authorityId);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
