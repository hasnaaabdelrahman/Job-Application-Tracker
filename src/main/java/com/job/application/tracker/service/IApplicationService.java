package com.job.application.tracker.service;

import com.job.application.tracker.dto.*;
import com.job.application.tracker.entity.Application;

import java.util.List;
import java.util.Map;

public interface IApplicationService {
    ApplicationGetDto add(ApplicationCreateDto dto);
    List<ApplicationGetDto> get();
    List<ApplicationsByCompanyDto> getByCompany(Integer id);
    List<ApplicationGetDto> getByUser(Integer id);
    List<ApplicationByStatusDto> getByStatus(Application.ApplicationStatus status);
    Map<Application.ApplicationStatus , Long> getStats();
    ApplicationGetDto update(Integer id, ApplicationUpdateDto dto);
    void delete(Application application);
}
