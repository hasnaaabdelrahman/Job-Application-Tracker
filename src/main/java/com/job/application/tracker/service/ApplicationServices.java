package com.job.application.tracker.service;

import com.job.application.tracker.dto.*;
import com.job.application.tracker.entity.Application;
import com.job.application.tracker.entity.Company;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.mapper.ApplicationMapper;
import com.job.application.tracker.mapper.CompanyMapper;
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
public class ApplicationServices {
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    CompanyRepository companyRepository;
    public ApplicationGetDto add(ApplicationCreateDto dto) {
        Application application = ApplicationMapper.toEntity(dto);
        User user =   userRepository.findById(dto.getUser_id()).orElseThrow();

        Job job = jobRepository.findById(dto.getJob_id())
                .orElseThrow();
        if(applicationRepository.existsByUserIdAndJobId(user.getId() , job.getId())) {
            throw new RuntimeException("You have already applied for this job");
        }
        application.setUser(user);
        application.setJob(job);

        applicationRepository.save(application);
         return ApplicationMapper.toDto(application);
    }
    public List<ApplicationGetDto> get() {
        return applicationRepository.findAll()
                .stream()
                .map(application -> new ApplicationGetDto(application.getId() , application.getApplicationStatus()))
                .toList();
    }
    public List<ApplicationsByCompanyDto> getByCompany(Integer id) {
        Company company = companyRepository.findById(id).orElseThrow();
        return applicationRepository.findAll()
                .stream()
                .map(application -> new ApplicationsByCompanyDto(application.getId() , application.getApplicationStatus() , application.getJob().getCompany().getName()))
                .toList();
    }
    public List<ApplicationGetDto> getByUser(Integer id) {
        return applicationRepository.findByUserId(id).stream()
                .map(application -> new ApplicationGetDto(application.getId() , application.getApplicationStatus()))
                .toList();
    }
    public List<ApplicationByStatusDto> getByStatus(Application.ApplicationStatus status) {
        return applicationRepository.findByApplicationStatus(status).stream()
                .map(app -> new ApplicationByStatusDto(app.getId() , app.getApplicationStatus()))
                .toList();
    }
    public Map<Application.ApplicationStatus , Long> getStats() {
        return applicationRepository.findAll().stream()
                .collect(Collectors.groupingBy(Application::getApplicationStatus , Collectors.counting()));
    }
    public ApplicationGetDto update(Integer id, ApplicationUpdateDto dto) {
        Application application = applicationRepository.findById(id).orElseThrow();
        ApplicationMapper.update(application , dto);
        applicationRepository.save(application);
        return ApplicationMapper.toDto(application);
    }
    public void delete(Application application) {
        if (application.getApplicationStatus() == Application.ApplicationStatus.REJECTED) {
            applicationRepository.delete(application);
        }
    }

}
