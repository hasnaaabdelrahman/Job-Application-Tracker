package com.job.application.tracker.controller;

import com.job.application.tracker.dto.JobCreateDto;
import com.job.application.tracker.dto.JobGetDto;
import com.job.application.tracker.dto.JobUpdateDto;
import com.job.application.tracker.dto.JobsDto;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/job/v1")
public class JobController {
    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<JobGetDto> get(@PathVariable("id") Integer id) {
        JobGetDto job = jobService.get(id);
        return ResponseEntity.ok(job);
    }
    @GetMapping("/getByCompany/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<JobsDto>> getByCompany(@PathVariable("id") Integer id) {
        final List<JobsDto> jobs = jobService.getAllByCompany(id);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/get-all")
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

    @GetMapping("getByTitle/{title}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<JobsDto>> getByTitle(@PathVariable("title") String title) {
        final List<JobsDto> jobs = jobService.getByTitle(title);
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobGetDto> addJob(@Valid @RequestBody JobCreateDto dto) {
        final JobGetDto added = jobService.add(dto);
        return ResponseEntity.ok(added);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<JobGetDto> updateJob(@PathVariable("id") Integer id,@Valid @RequestBody JobUpdateDto dto) {
        final JobGetDto updated = jobService.update(id,dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Job> deleteJob(@PathVariable("id") Integer id) {
        jobService.delete(id);
        return ResponseEntity.ok().build();
    }
}
