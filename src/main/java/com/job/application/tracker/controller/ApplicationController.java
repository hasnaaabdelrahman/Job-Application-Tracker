package com.job.application.tracker.controller;

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
    public ResponseEntity<List<Application>> getAll() {
        final List<Application> applications = applicationServices.get();
        return ResponseEntity.ok(applications);
    }
    @PostMapping("/add")
    public ResponseEntity<Application> add(@RequestBody Application application) {
        final Application applicationAdded = applicationServices.add(application);
        return ResponseEntity.ok(applicationAdded);
    }
    @PutMapping("/update")
    public ResponseEntity<Application> update(@RequestBody Application application) {
        final Application applicationUpdated= applicationServices.update(application);
        return ResponseEntity.ok(applicationUpdated);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Application> delete(@RequestBody Application application) {
      applicationServices.delete(application);
        return ResponseEntity.ok().build();
    }
}
