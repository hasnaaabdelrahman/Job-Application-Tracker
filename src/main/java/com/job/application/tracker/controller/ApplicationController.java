package com.job.application.tracker.controller;

import com.job.application.tracker.model.dto.*;
import com.job.application.tracker.model.entity.Application;
import com.job.application.tracker.service.ApplicationService;
import com.job.application.tracker.model.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "5- Application")
@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Operation(summary = "1- Get application")
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApplicationGetDto> getApplication(@PathVariable("id") Integer id ,
                                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        ApplicationGetDto application;
        if(userDetails.getUsername().equals("admin@gmail.com")) {
            application = applicationService.get(id);

        }else {
            application = applicationService.get(userDetails.getId() , id);
        }
        return ResponseEntity.ok(application);
    }

    @Operation(summary = "2- Get all applications")
    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationGetDto>> getAll(@RequestParam(defaultValue = "0") int page ,
                                                          @RequestParam(defaultValue = "5") int size ,
                                                          @RequestParam(defaultValue = "id") String sortBy ,
                                                          @RequestParam(defaultValue = "true") boolean ascending
            ,@AuthenticationPrincipal CustomUserDetails userDetails)
    {
        Sort sort = ascending? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page , size , sort);
        final List<ApplicationGetDto> applications ;
        if (userDetails.getUsername().equals("admin@gmail.com")) {
            applications =applicationService.get(pageable);
        } else {
            applications = applicationService.getAllByUser(userDetails.getId() , pageable);
        }
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "3- Get applications by company")
    @GetMapping("/companies/{id}/applications")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationsByCompanyDto>> get(@PathVariable("id") Integer id
    , @AuthenticationPrincipal CustomUserDetails userDetails) {
        final List<ApplicationsByCompanyDto> applications ;
        if (userDetails.getUsername().equals("admin@gmail.com")) {
            applications = applicationService.getByCompany(id);
        } else {
            applications = applicationService.getByCompanyForUser(id, userDetails.getId());
        }
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "4- Get applications by status")
    @GetMapping("/search")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationByStatusDto>> get(@RequestParam Application.ApplicationStatus status
            , @AuthenticationPrincipal CustomUserDetails userDetails) {
        final List<ApplicationByStatusDto> applications ;
        if(userDetails.getUsername().equals("admin@gmail.com")) {
            applications = applicationService.getByStatus(status);
        }else {
            applications = applicationService.getByStatusForUser(status , userDetails.getId());
        }
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "5- Get applications by user")
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<ApplicationGetDto>> getByUser(@PathVariable("id") Integer id) {
        final List<ApplicationGetDto> applications = applicationService.getByUser(id);
        return ResponseEntity.ok(applications);
    }

    @Operation(summary = "6- Get applications stats")
    @GetMapping("/stats")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<Application.ApplicationStatus , Long>> getStats(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if(userDetails.getUsername().equals("admin@gmail.com")) {
            return ResponseEntity.ok(applicationService.getStats());
        }
      else
        {
            return ResponseEntity.ok(applicationService.getStats(userDetails.getId()));
        }
    }

    @Operation(summary = "7- Create application")
    @PostMapping("/add")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApplicationGetDto> add(@Valid @RequestBody ApplicationCreateDto dto) {
        final ApplicationGetDto applicationAdded = applicationService.add(dto);
        return ResponseEntity.ok(applicationAdded);
    }

    @Operation(summary = "8- Update application")
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApplicationGetDto> update(@PathVariable("id") Integer id,@Valid @RequestBody ApplicationUpdateDto dto) {
        final ApplicationGetDto applicationUpdated= applicationService.update(id,dto);
        return ResponseEntity.ok(applicationUpdated);
    }

    @Operation(summary = "9- Delete applications")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Application> delete(@Valid @PathVariable("id") Integer id) {
      applicationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
