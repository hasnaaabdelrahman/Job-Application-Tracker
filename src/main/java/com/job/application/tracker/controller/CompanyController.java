package com.job.application.tracker.controller;

import com.job.application.tracker.entity.Company;
import com.job.application.tracker.service.CompanyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/company/v1")
public class CompanyController {

    private final CompanyServices companyServices;

    @Autowired
    public CompanyController(CompanyServices companyServices) {
        this.companyServices = companyServices;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Company>> getAll() {
        final List<Company> companies = companyServices.getAll();
        return ResponseEntity.ok(companies);
    }
    @PostMapping("/add")
    public ResponseEntity<Company> add(@RequestBody Company company) {
        final Company addedCompany = companyServices.add(company);
        return ResponseEntity.ok(addedCompany);
    }
}
