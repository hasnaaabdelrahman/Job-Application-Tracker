package com.job.application.tracker.service;

import com.job.application.tracker.dto.*;
import com.job.application.tracker.entity.Application;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IApplicationService {
    ApplicationGetDto add(ApplicationCreateDto dto);
    List<ApplicationGetDto> get(Pageable pageable);
    ApplicationGetDto get(Integer id);
    ApplicationGetDto get(Integer userId , Integer applivationId);
    List<ApplicationsByCompanyDto> getByCompany(Integer id);
    List<ApplicationGetDto> getByUser(Integer id);
    List<ApplicationByStatusDto> getByStatus(Application.ApplicationStatus status);
    Map<Application.ApplicationStatus , Long> getStats();
    Map<Application.ApplicationStatus , Long> getStats(Integer id);
    ApplicationGetDto update(Integer id, ApplicationUpdateDto dto);
    void delete(Integer id);
    List<ApplicationsByCompanyDto>getByCompanyForUser(Integer id , Integer userId);
    List<ApplicationByStatusDto>getByStatusForUser(Application.ApplicationStatus status , Integer userId);
    List<ApplicationGetDto> getAllByUser(Integer id , Pageable pageable);

}
