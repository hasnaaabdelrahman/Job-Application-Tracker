package com.job.application.tracker.controller;

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
    public ResponseEntity<List<Job>> getAll() {
        final List<Job> jobs = jobServices.showAll();
        return ResponseEntity.ok(jobs);
    }
    @PostMapping("/add")
    public ResponseEntity<Job> addUser(@RequestBody Job job) {
        final Job added = jobServices.add(job);
        return ResponseEntity.ok(added);
    }
    @PutMapping("/update")
    public ResponseEntity<Job> updateUser(@RequestBody Job job) {
        final Job updated = jobServices.update(job);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Job> deleteUser(@PathVariable("id") Integer id) {
        jobServices.delete(id);
        return ResponseEntity.ok().build();
    }
}
