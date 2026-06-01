package com.job.application.tracker.controller;

import com.job.application.tracker.model.dto.company.CompanyRequest;
import com.job.application.tracker.model.dto.company.CompanyResponse;
import com.job.application.tracker.model.dto.company.CompanyUpdateRequest;
import com.job.application.tracker.model.entity.Company;
import com.job.application.tracker.service.implementation.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "3- Company")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/company/v1")
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "1- Get all companies")
    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CompanyResponse>> getAll(@RequestParam(defaultValue = "0") int page ,
                                                        @RequestParam(defaultValue = "5") int size ,
                                                        @RequestParam(defaultValue = "id") String sortBy ,
                                                        @RequestParam(defaultValue = "true") boolean ascending)
    {
        Sort sort = ascending? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page , size , sort);
        final List<CompanyResponse> companies = companyService.getAll(pageable);
        return ResponseEntity.ok(companies);
    }
    @Operation(summary = "2- Get by company id")
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CompanyResponse> get(@PathVariable("id") Integer id) {
        final CompanyResponse company = companyService.get(id);
        return ResponseEntity.ok(company);
    }
    @Operation(summary = "3- Create company")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponse> add(@Valid @RequestBody CompanyRequest dto) {
        final CompanyResponse addedCompany = companyService.add(dto);
        return ResponseEntity.ok(addedCompany);
    }
    @Operation(summary = "4- Update company")
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CompanyResponse> update(@PathVariable("id") Integer id , @Valid @RequestBody CompanyUpdateRequest dto) {
        final CompanyResponse company = companyService.update(id , dto);
        return ResponseEntity.ok(company);
    }

    @Operation(summary = "5- Delete company")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Company> delete(@PathVariable("id") Integer id) {
        companyService.delete(id);
        return ResponseEntity.ok().build();
    }

}
