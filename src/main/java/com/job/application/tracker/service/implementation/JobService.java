package com.job.application.tracker.service.implementation;

import com.job.application.tracker.common.JobType;
import com.job.application.tracker.model.dto.job.*;
import com.job.application.tracker.model.entity.Company;
import com.job.application.tracker.model.entity.Job;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.mapper.JobMapper;
import com.job.application.tracker.repository.CompanyRepository;
import com.job.application.tracker.repository.JobRepository;
import com.job.application.tracker.repository.specification.JobSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService implements com.job.application.tracker.service.JobService {
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    @Override
    public JobResponse add(JobRequest job) {
        Job jobAdded =  JobMapper.toEntity(job);
        Company company = companyRepository.findById(job.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + job.getCompanyId()));

        jobAdded.setCompany(company);
         jobRepository.save(jobAdded);
         return JobMapper.toDto(jobAdded);
    }

    @Override
    public List<JobsResponse> getAllByCompany(Pageable pageable, Integer id) {
        return  jobRepository.findByCompanyId(id).stream()
                .map(job -> new JobsResponse(job.getId() , job.getTitle() , job.getDescription() ,
                        job.getSalary() , job.getLocation() , job.getType()))
                .toList();
    }

    public List<JobsResponse> getAllByCompany(Integer id) {
        return  jobRepository.findByCompanyId(id).stream()
                .map(job -> new JobsResponse(job.getId() , job.getTitle() , job.getDescription() ,
                        job.getSalary() , job.getLocation() , job.getType()))
                .toList();
    }

    @Override
    public JobResponse get(Integer id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        return JobMapper.toDto(job);
    }

    @Override
    public List<JobsResponse> getByTitle(String title) {
        return jobRepository.findByTitleContainingIgnoreCase(title)
                .stream().map(job -> new JobsResponse(job.getId() , job.getTitle() , job.getDescription() ,
                        job.getSalary() , job.getLocation() , job.getType()))
                .toList();
    }

    public List<JobResponse> search(String title , String location , JobType type, Long minSalary) {
        Specification<Job> spec = Specification.unrestricted();
         if(title != null && !title.isBlank()) {
                spec = spec.and(JobSpecification.hasTitle(title));
         }
        if(type != null ) {
            spec = spec.and(JobSpecification.hasType(String.valueOf(type)));
        }
        if(location != null && !location.isBlank()) {
            spec = spec.and(JobSpecification.hasLocation(location));
        }
        if(minSalary != null) {
            spec = spec.and(JobSpecification.hasSalary(minSalary));
        }
        return jobRepository.findAll(spec)
                .stream().map(job ->
                        new JobResponse(job.getId(), job.getTitle(), job.getDescription() , job.getCompany().getId() ,
                                job.getSalary() , job.getLocation() , job.getType())).toList();
    }

    @Override
    public List<JobResponse> showAll(Pageable pageable) {
        return jobRepository.findAll(pageable)
                .stream()
                .map(job -> new JobResponse(job.getId(), job.getTitle(), job.getDescription() , job.getCompany().getId() ,
                        job.getSalary() , job.getLocation() , job.getType()))
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
    public JobResponse update(Integer id , JobUpdateRequest dto) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        JobMapper.update(job , dto);
        Job updatedjob = jobRepository.save(job);
        return JobMapper.toDto(updatedjob);
    }

    public JobApplicationsRequest countApplications(int id) {
        return jobRepository.countApplicationsByCompanyId(id);
    }
}
