package com.job.application.tracker.controller;

import com.job.application.tracker.dto.JobCreateDto;
import com.job.application.tracker.dto.JobGetDto;
import com.job.application.tracker.dto.JobUpdateDto;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.service.JobServices;
import com.job.application.tracker.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/job/v1")
public class JobController {
    private final JobServices jobServices;

    @Autowired
    public JobController(JobServices jobServices) {
        this.jobServices = jobServices;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<JobGetDto>> getAll() {
        final List<JobGetDto> jobs = jobServices.showAll();
        return ResponseEntity.ok(jobs);
    }
    @PostMapping("/add")
    public ResponseEntity<JobGetDto> addJob(@RequestBody JobCreateDto dto) {
        final JobGetDto added = jobServices.add(dto);
        return ResponseEntity.ok(added);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<JobGetDto> updateJob(@PathVariable("id") Integer id, @RequestBody JobUpdateDto dto) {
        final JobGetDto updated = jobServices.update(id,dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Job> deleteJob(@PathVariable("id") Integer id) {
        jobServices.delete(id);
        return ResponseEntity.ok().build();
    }
}
