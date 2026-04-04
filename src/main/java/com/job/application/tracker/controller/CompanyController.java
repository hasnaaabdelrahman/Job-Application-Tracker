package com.job.application.tracker.controller;

import com.job.application.tracker.dto.CompanyCreateDto;
import com.job.application.tracker.dto.CompanyGetDto;
import com.job.application.tracker.dto.CompanyUpdateDto;
import com.job.application.tracker.entity.Company;
import com.job.application.tracker.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/company/v1")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CompanyGetDto>> getAll() {
        final List<CompanyGetDto> companies = companyService.getAll();
        return ResponseEntity.ok(companies);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<CompanyGetDto> get(@PathVariable("id") Integer id) {
        final CompanyGetDto company = companyService.get(id);
        return ResponseEntity.ok(company);
    }
    @PostMapping("/add")
    public ResponseEntity<CompanyGetDto> add(@Valid @RequestBody CompanyCreateDto dto) {
        final CompanyGetDto addedCompany = companyService.add(dto);
        return ResponseEntity.ok(addedCompany);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CompanyGetDto> update(@PathVariable("id") Integer id ,@Valid @RequestBody CompanyUpdateDto dto) {
        final CompanyGetDto company = companyService.update(id , dto);
        return ResponseEntity.ok(company);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Company> delete(@PathVariable("id") Integer id) {
        companyService.delete(id);
        return ResponseEntity.ok().build();
    }

}
