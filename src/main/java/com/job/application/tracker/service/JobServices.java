package com.job.application.tracker.service;

import com.job.application.tracker.dto.JobCreateDto;
import com.job.application.tracker.dto.JobGetDto;
import com.job.application.tracker.dto.JobUpdateDto;
import com.job.application.tracker.entity.Company;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.mapper.JobMapper;
import com.job.application.tracker.repository.CompanyRepository;
import com.job.application.tracker.repository.JobRepository;
import com.job.application.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class JobServices {
    @Autowired
    JobRepository jobRepository;
    @Autowired
    CompanyRepository companyRepository;

    public JobGetDto add(JobCreateDto job) {
        Job jobAdded =  JobMapper.toEntity(job);
        Company company = companyRepository.findById(job.getCompanyId())
                .orElseThrow();

        jobAdded.setCompany(company);
         jobRepository.save(jobAdded);
         return JobMapper.toDto(jobAdded);
    }

    public JobGetDto get(Integer id) {
        Job job = jobRepository.findById(id).orElseThrow();
        return JobMapper.toDto(job);
    }
    public List<JobGetDto> showAll() {
        return jobRepository.findAll()
                .stream()
                .map(job -> new JobGetDto(job.getId(), job.getTitle(), job.getDescription() , job.getCompany().getId()))
                .toList();
    }
    public void delete(Integer id) {
        jobRepository.deleteById(id);
    }
    public JobGetDto update(Integer id , JobUpdateDto dto) {
        Job job = jobRepository.findById(id).orElseThrow();
        JobMapper.update(job , dto);
        Job updatedjob = jobRepository.save(job);
        return JobMapper.toDto(updatedjob);
    }
}
