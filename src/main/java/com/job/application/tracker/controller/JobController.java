package com.job.application.tracker.controller;

import com.job.application.tracker.dto.JobCreateDto;
import com.job.application.tracker.dto.JobGetDto;
import com.job.application.tracker.dto.JobUpdateDto;
import com.job.application.tracker.dto.JobsDto;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<JobGetDto> get(@PathVariable("id") Integer id) {
        JobGetDto job = jobService.get(id);
        return ResponseEntity.ok(job);
    }
    @GetMapping("/getByCompany/{id}")
    public ResponseEntity<List<JobsDto>> getByCompany(@PathVariable("id") Integer id) {
        final List<JobsDto> jobs = jobService.getAllByCompany(id);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<JobGetDto>> getAll() {
        final List<JobGetDto> jobs = jobService.showAll();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("getByTitle/{title}")
    public ResponseEntity<List<JobsDto>> getByTitle(String title) {
        final List<JobsDto> jobs = jobService.getByTitle(title);
        return ResponseEntity.ok(jobs);
    }

    @PostMapping("/add")
    public ResponseEntity<JobGetDto> addJob(@Valid @RequestBody JobCreateDto dto) {
        final JobGetDto added = jobService.add(dto);
        return ResponseEntity.ok(added);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<JobGetDto> updateJob(@PathVariable("id") Integer id,@Valid @RequestBody JobUpdateDto dto) {
        final JobGetDto updated = jobService.update(id,dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Job> deleteJob(@PathVariable("id") Integer id) {
        jobService.delete(id);
        return ResponseEntity.ok().build();
    }
}
