package com.job.application.tracker.controller;

import com.job.application.tracker.model.dto.JobCreateDto;
import com.job.application.tracker.model.dto.JobGetDto;
import com.job.application.tracker.model.dto.JobUpdateDto;
import com.job.application.tracker.model.dto.JobsDto;
import com.job.application.tracker.model.entity.Job;
import com.job.application.tracker.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "4- Job")
@RestController
@RequestMapping("/api/v1/job")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Operation(summary = "1- Get job")
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<JobGetDto> get(@PathVariable("id") Integer id) {
        JobGetDto job = jobService.get(id);
        return ResponseEntity.ok(job);
    }
    @Operation(summary = "2- Get jobs by company")
    @GetMapping("/companies/{id}/jobs")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<JobsDto>> getByCompany(@PathVariable("id") Integer id) {
        final List<JobsDto> jobs = jobService.getAllByCompany(id);
        return ResponseEntity.ok(jobs);
    }
    @Operation(summary = "3- Get all jobs")
    @GetMapping("/get")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<JobGetDto>> getAll(@RequestParam(defaultValue = "0") int page ,
                                                  @RequestParam(defaultValue = "5") int size ,
                                                  @RequestParam(defaultValue = "id") String sortBy ,
                                                  @RequestParam(defaultValue = "true") boolean ascending)

    {
          Sort sort = ascending? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
          Pageable pageable = PageRequest.of(page , size , sort);
        final List<JobGetDto> jobs = jobService.showAll(pageable);
        return ResponseEntity.ok(jobs);
    }

    @Operation(summary = "4- Search jobs by title")
    @GetMapping("search/{title}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<JobsDto>> getByTitle(@PathVariable("title") String title) {
        final List<JobsDto> jobs = jobService.getByTitle(title);
        return ResponseEntity.ok(jobs);
    }

    @Operation(summary = "5- Create job")
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobGetDto> addJob(@Valid @RequestBody JobCreateDto dto) {
        final JobGetDto added = jobService.add(dto);
        return ResponseEntity.ok(added);
    }

    @Operation(summary = "6- Update job")
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobGetDto> updateJob(@PathVariable("id") Integer id,@Valid @RequestBody JobUpdateDto dto) {
        final JobGetDto updated = jobService.update(id,dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "7- Delete job")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Job> deleteJob(@PathVariable("id") Integer id) {
        jobService.delete(id);
        return ResponseEntity.ok().build();
    }
}
