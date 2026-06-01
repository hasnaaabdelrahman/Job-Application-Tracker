package com.job.application.tracker.service.implementation;

import com.job.application.tracker.common.ApplicationStatus;
import com.job.application.tracker.model.dto.application.*;
import com.job.application.tracker.model.entity.Application;
import com.job.application.tracker.model.entity.Company;
import com.job.application.tracker.model.entity.Job;
import com.job.application.tracker.model.entity.User;
import com.job.application.tracker.exceptions.DuplicateApplicationException;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.mapper.ApplicationMapper;
import com.job.application.tracker.repository.ApplicationRepository;
import com.job.application.tracker.repository.CompanyRepository;
import com.job.application.tracker.repository.JobRepository;
import com.job.application.tracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ApplicationService  implements com.job.application.tracker.service.ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;


    @Override
    public ApplicationResponse add(ApplicationCreateRequest dto) {
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
    public List<ApplicationResponse> get(Pageable pageable) {
        return applicationRepository.findAll(pageable)
                .stream()
                .map(application -> new ApplicationResponse(application.getId() , application.getApplicationStatus()))
                .toList();
    }
    @Override
    public ApplicationResponse get(Integer id) {
        Application application= applicationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("application not found with id: " + id));
        return ApplicationMapper.toDto(application);
    }
    @Override
    public ApplicationResponse get(Integer userId , Integer applicationId) {
        Application application= applicationRepository.findByUser_IdAndId(userId , applicationId)
                .orElseThrow(()-> new ResourceNotFoundException("application not found with id: " + applicationId));
        return ApplicationMapper.toDto(application);
    }

    public List<ApplicationResponse> getAllByUser(Integer id , Pageable pageable) {
        return applicationRepository.findByUser_Id(id , pageable)
                .stream()
                .map(application -> new ApplicationResponse(application.getId() , application.getApplicationStatus()))
                .toList();
    }

    @Override
    public List<ApplicationsByCompanyRequest> getByCompany(Integer id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("company not found with id:" + id));
        return applicationRepository.findByJob_Company_Id(company.getId())
                .stream()
                .map(application -> new ApplicationsByCompanyRequest(application.getId() , application.getApplicationStatus() , application.getJob().getCompany().getName()))
                .toList();
    }

    public  List<ApplicationsByCompanyRequest>getByCompanyForUser(Integer id , Integer userId) {
        return applicationRepository.findByJobCompanyIdAndUserId(id , userId).stream()
                .map(app -> new ApplicationsByCompanyRequest(app.getId() , app.getApplicationStatus() , app.getJob().getCompany().getName()))
                .toList();
    }

    @Override
    public List<ApplicationResponse> getByUser(Integer id) {
        return applicationRepository.findByUserId(id).stream()
                .map(application -> new ApplicationResponse(application.getId() , application.getApplicationStatus()))
                .toList();
    }

    @Override
    public List<ApplicationByStatusRequest> getByStatus(ApplicationStatus status) {
        return applicationRepository.findByApplicationStatus(status).stream()
                .map(app -> new ApplicationByStatusRequest(app.getId() , app.getApplicationStatus()))
                .toList();
    }
    @Override
    public List<ApplicationByStatusRequest> getByStatusForUser(ApplicationStatus status , Integer id) {
        return applicationRepository.findByApplicationStatusAndUserId(status , id).stream()
                .map(app -> new ApplicationByStatusRequest(app.getId() , app.getApplicationStatus()))
                .toList();
    }

    @Override
    public Map<ApplicationStatus , Long> getStats() {
        return applicationRepository.findAll().stream()
                .collect(Collectors.groupingBy(Application::getApplicationStatus , Collectors.counting()));
    }
    @Override
    public Map<ApplicationStatus , Long> getStats(Integer id) {
        return applicationRepository.findByUserId(id).stream()
                .collect(Collectors.groupingBy(Application::getApplicationStatus , Collectors.counting()));
    }

    @Override
    public ApplicationResponse update(Integer id, ApplicationUpdateRequest dto) {
        Application application = applicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("application not found with id:"+ id));
        ApplicationMapper.update(application , dto);
        applicationRepository.save(application);
        return ApplicationMapper.toDto(application);
    }

    @Override
    public void delete(Integer id) {
        Application application = applicationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("application not found with id: " + id));
        applicationRepository.deleteById(id);
    }

}
