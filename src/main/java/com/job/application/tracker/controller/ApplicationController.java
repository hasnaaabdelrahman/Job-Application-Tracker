package com.job.application.tracker.controller;

import com.job.application.tracker.dto.*;
import com.job.application.tracker.entity.Application;
import com.job.application.tracker.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/application/v1")
public class ApplicationController {

    private final ApplicationService applicationService;
    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationGetDto>> getAll(@RequestParam(defaultValue = "0") int page ,
                                                          @RequestParam(defaultValue = "5") int size ,
                                                          @RequestParam(defaultValue = "id") String sortBy ,
                                                          @RequestParam(defaultValue = "true") boolean ascending)
    {
        Sort sort = ascending? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page , size , sort);
        final List<ApplicationGetDto> applications = applicationService.get(pageable);
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/getByCompany/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationsByCompanyDto>> get(@PathVariable("id") Integer id) {
        final List<ApplicationsByCompanyDto> applications = applicationService.getByCompany(id);
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/getByStatus")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationByStatusDto>> get(@RequestParam Application.ApplicationStatus status) {
        final List<ApplicationByStatusDto> applications = applicationService.getByStatus(status);
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/getByUser/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationGetDto>> getByUser(@PathVariable("id") Integer id) {
        final List<ApplicationGetDto> applications = applicationService.getByUser(id);
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/getStats")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<Application.ApplicationStatus , Long>> getStats() {
        return ResponseEntity.ok(applicationService.getStats());
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApplicationGetDto> add(@Valid @RequestBody ApplicationCreateDto dto) {
        final ApplicationGetDto applicationAdded = applicationService.add(dto);
        return ResponseEntity.ok(applicationAdded);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApplicationGetDto> update(@PathVariable("id") Integer id,@Valid @RequestBody ApplicationUpdateDto dto) {
        final ApplicationGetDto applicationUpdated= applicationService.update(id,dto);
        return ResponseEntity.ok(applicationUpdated);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Application> delete(@Valid @PathVariable("id") Integer id) {
      applicationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
