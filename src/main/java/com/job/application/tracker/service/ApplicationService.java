package com.job.application.tracker.service;

import com.job.application.tracker.common.ApplicationStatus;
import com.job.application.tracker.model.dto.application.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ApplicationService {
    ApplicationResponse add(ApplicationCreateRequest dto);
    List<ApplicationResponse> get(Pageable pageable);
    ApplicationResponse get(Integer id);
    ApplicationResponse get(Integer userId , Integer applivationId);
    List<ApplicationsByCompanyRequest> getByCompany(Integer id);
    List<ApplicationResponse> getByUser(Integer id);
    List<ApplicationByStatusRequest> getByStatus(ApplicationStatus status);
    Map<ApplicationStatus, Long> getStats();
    Map<ApplicationStatus , Long> getStats(Integer id);
    ApplicationResponse update(Integer id, ApplicationUpdateRequest dto);
    void delete(Integer id);
    List<ApplicationsByCompanyRequest>getByCompanyForUser(Integer id , Integer userId);
    List<ApplicationByStatusRequest>getByStatusForUser(ApplicationStatus status , Integer userId);
    List<ApplicationResponse> getAllByUser(Integer id , Pageable pageable);

}
