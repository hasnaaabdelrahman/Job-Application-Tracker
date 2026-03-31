package com.job.application.tracker.controller;

import com.job.application.tracker.dto.CompanyCreateDto;
import com.job.application.tracker.dto.CompanyGetDto;
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
    public ResponseEntity<CompanyGetDto> add(@RequestBody CompanyCreateDto dto) {
        final CompanyGetDto addedCompany = companyServices.add(dto);
        return ResponseEntity.ok(addedCompany);
    }
}
