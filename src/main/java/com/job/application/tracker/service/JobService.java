package com.job.application.tracker.service;

import com.job.application.tracker.dto.JobCreateDto;
import com.job.application.tracker.dto.JobGetDto;
import com.job.application.tracker.dto.JobUpdateDto;
import com.job.application.tracker.dto.JobsDto;
import com.job.application.tracker.entity.Company;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.mapper.JobMapper;
import com.job.application.tracker.repository.CompanyRepository;
import com.job.application.tracker.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService implements IJobService {
    @Autowired
    JobRepository jobRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public JobGetDto add(JobCreateDto job) {
        Job jobAdded =  JobMapper.toEntity(job);
        Company company = companyRepository.findById(job.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + job.getCompanyId()));

        jobAdded.setCompany(company);
         jobRepository.save(jobAdded);
         return JobMapper.toDto(jobAdded);
    }

    @Override
    public List<JobsDto> getAllByCompany(Integer id) {
        return  jobRepository.findByCompanyId(id).stream()
                .map(job -> new JobsDto(job.getId() , job.getTitle() , job.getDescription()))
                .toList();
    }

    @Override
    public JobGetDto get(Integer id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        return JobMapper.toDto(job);
    }

    @Override
    public List<JobsDto> getByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title)
                .stream().map(job -> new JobsDto(job.getId() , job.getTitle() , job.getDescription()))
                .toList();
    }

    @Override
    public List<JobGetDto> showAll(Pageable pageable) {
        return jobRepository.findAll(pageable)
                .stream()
                .map(job -> new JobGetDto(job.getId(), job.getTitle(), job.getDescription() , job.getCompany().getId()))
                .toList();
    }

    @Override
    public void delete(Integer id) {
        if(!jobRepository.existsById(id)) {
            throw new ResourceNotFoundException("Job not found with id: " + id);
        }
        jobRepository.deleteById(id);
    }

    @Override
    public JobGetDto update(Integer id , JobUpdateDto dto) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        JobMapper.update(job , dto);
        Job updatedjob = jobRepository.save(job);
        return JobMapper.toDto(updatedjob);
    }
}
