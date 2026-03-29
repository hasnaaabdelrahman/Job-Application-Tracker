package com.job.application.tracker.service;

import com.job.application.tracker.entity.Job;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.repository.JobRepository;
import com.job.application.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServices {
    @Autowired
    JobRepository jobRepository;

    public Job add(Job job) {
        Job jobAdded = jobRepository.save(job);
        return jobAdded;
    }
    public List<Job> showAll() {
        return jobRepository.findAll();
    }
    public void delete(Integer id) {
        jobRepository.deleteById(id);
    }
    public Job update(Job job) {
        Job updatedjob = jobRepository.save(job);
        return updatedjob;
    }
}
