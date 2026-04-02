package com.job.application.tracker.controller;

import com.job.application.tracker.dto.*;
import com.job.application.tracker.entity.Application;
import com.job.application.tracker.service.ApplicationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/application/v1")
public class ApplicationController {

    private final ApplicationServices applicationServices;
    @Autowired
    public ApplicationController(ApplicationServices applicationServices) {
        this.applicationServices = applicationServices;
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<ApplicationGetDto>> getAll() {
        final List<ApplicationGetDto> applications = applicationServices.get();
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/getByCompany/{id}")
    public ResponseEntity<List<ApplicationsByCompanyDto>> get(@PathVariable("id") Integer id) {
        final List<ApplicationsByCompanyDto> applications = applicationServices.getByCompany(id);
        return ResponseEntity.ok(applications);
    }
    @GetMapping("/getByStatus")
    public ResponseEntity<List<ApplicationByStatusDto>> get(@RequestParam Application.ApplicationStatus status) {
        final List<ApplicationByStatusDto> applications = applicationServices.getByStatus(status);
        return ResponseEntity.ok(applications);
    }
    @PostMapping("/add")
    public ResponseEntity<ApplicationGetDto> add(@RequestBody ApplicationCreateDto dto) {
        final ApplicationGetDto applicationAdded = applicationServices.add(dto);
        return ResponseEntity.ok(applicationAdded);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApplicationGetDto> update(@PathVariable("id") Integer id,@RequestBody ApplicationUpdateDto dto) {
        final ApplicationGetDto applicationUpdated= applicationServices.update(id,dto);
        return ResponseEntity.ok(applicationUpdated);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Application> delete(@RequestBody Application application) {
      applicationServices.delete(application);
        return ResponseEntity.ok().build();
    }
}
