package com.job.application.tracker.service;

import com.job.application.tracker.dto.*;
import com.job.application.tracker.entity.Application;
import com.job.application.tracker.entity.Company;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.exceptions.DuplicateApplicationException;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.mapper.ApplicationMapper;
import com.job.application.tracker.repository.ApplicationRepository;
import com.job.application.tracker.repository.CompanyRepository;
import com.job.application.tracker.repository.JobRepository;
import com.job.application.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApplicationService  implements  IApplicationService{
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public ApplicationGetDto add(ApplicationCreateDto dto) {
        Application application = ApplicationMapper.toEntity(dto);
        User user =   userRepository.findById(dto.getUser_id()).orElseThrow(() -> new ResourceNotFoundException("user not found with id: "+ dto.getUser_id()));

        Job job = jobRepository.findById(dto.getJob_id())
                .orElseThrow(() -> new ResourceNotFoundException("job not found with id: "+dto.getJob_id()));
        if(applicationRepository.existsByUserIdAndJobId(user.getId() , job.getId())) {
            throw new DuplicateApplicationException("You have already applied for this job");
        }
        application.setUser(user);
        application.setJob(job);

        applicationRepository.save(application);
         return ApplicationMapper.toDto(application);
    }

    @Override
    public List<ApplicationGetDto> get() {
        return applicationRepository.findAll()
                .stream()
                .map(application -> new ApplicationGetDto(application.getId() , application.getApplicationStatus()))
                .toList();
    }

    @Override
    public List<ApplicationsByCompanyDto> getByCompany(Integer id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("company not found with id:" + id));
        return applicationRepository.findAll()
                .stream()
                .map(application -> new ApplicationsByCompanyDto(application.getId() , application.getApplicationStatus() , application.getJob().getCompany().getName()))
                .toList();
    }

    @Override
    public List<ApplicationGetDto> getByUser(Integer id) {
        return applicationRepository.findByUserId(id).stream()
                .map(application -> new ApplicationGetDto(application.getId() , application.getApplicationStatus()))
                .toList();
    }

    @Override
    public List<ApplicationByStatusDto> getByStatus(Application.ApplicationStatus status) {
        return applicationRepository.findByApplicationStatus(status).stream()
                .map(app -> new ApplicationByStatusDto(app.getId() , app.getApplicationStatus()))
                .toList();
    }

    @Override
    public Map<Application.ApplicationStatus , Long> getStats() {
        return applicationRepository.findAll().stream()
                .collect(Collectors.groupingBy(Application::getApplicationStatus , Collectors.counting()));
    }

    @Override
    public ApplicationGetDto update(Integer id, ApplicationUpdateDto dto) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("application not found with id:"+ id));
        ApplicationMapper.update(application , dto);
        applicationRepository.save(application);
        return ApplicationMapper.toDto(application);
    }

    @Override
    public void delete(Application application) {
        if (!applicationRepository.existsById(application.getId())) {
            throw new ResourceNotFoundException("application not found with id" + application.getId());
        }
        if (application.getApplicationStatus() == Application.ApplicationStatus.REJECTED) {
            applicationRepository.delete(application);
        }
    }

}
