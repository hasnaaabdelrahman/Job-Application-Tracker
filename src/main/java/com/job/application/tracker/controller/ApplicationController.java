package com.job.application.tracker.controller;

import com.job.application.tracker.dto.*;
import com.job.application.tracker.entity.Application;
import com.job.application.tracker.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<ApplicationGetDto>> getAll() {
        final List<ApplicationGetDto> applications = applicationService.get();
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/getByCompany/{id}")
    public ResponseEntity<List<ApplicationsByCompanyDto>> get(@PathVariable("id") Integer id) {
        final List<ApplicationsByCompanyDto> applications = applicationService.getByCompany(id);
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/getByStatus")
    public ResponseEntity<List<ApplicationByStatusDto>> get(@RequestParam Application.ApplicationStatus status) {
        final List<ApplicationByStatusDto> applications = applicationService.getByStatus(status);
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/getByUser/{id}")
    public ResponseEntity<List<ApplicationGetDto>> getByUser(@PathVariable("id") Integer id) {
        final List<ApplicationGetDto> applications = applicationService.getByUser(id);
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/getStats")
    public ResponseEntity<Map<Application.ApplicationStatus , Long>> getStats() {
        return ResponseEntity.ok(applicationService.getStats());
    }
    @PostMapping("/add")
    public ResponseEntity<ApplicationGetDto> add(@Valid @RequestBody ApplicationCreateDto dto) {
        final ApplicationGetDto applicationAdded = applicationService.add(dto);
        return ResponseEntity.ok(applicationAdded);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationGetDto> update(@PathVariable("id") Integer id,@Valid @RequestBody ApplicationUpdateDto dto) {
        final ApplicationGetDto applicationUpdated= applicationService.update(id,dto);
        return ResponseEntity.ok(applicationUpdated);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Application> delete(@Valid @RequestBody Application application) {
      applicationService.delete(application);
        return ResponseEntity.ok().build();
    }
}
