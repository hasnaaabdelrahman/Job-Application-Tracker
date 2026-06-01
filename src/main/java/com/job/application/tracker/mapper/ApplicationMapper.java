package com.job.application.tracker.mapper;
import com.job.application.tracker.model.dto.application.ApplicationCreateRequest;
import com.job.application.tracker.model.dto.application.ApplicationResponse;
import com.job.application.tracker.model.dto.application.ApplicationUpdateRequest;
import com.job.application.tracker.model.entity.Application;


public class ApplicationMapper {
    public static Application toEntity(ApplicationCreateRequest dto) {
        if(dto == null) return null;
        Application application = new Application();
        application.setApplicationStatus(dto.getApplicationStatus());
        return application;
    }

    public static ApplicationResponse toDto(Application application) {
        if (application == null) return null;
        ApplicationResponse dto = new ApplicationResponse();
        dto.setId(application.getId());
        dto.setApplicationStatus(application.getApplicationStatus());
        return dto;
    }

    public static void update(Application application , ApplicationUpdateRequest dto) {
        if(application == null || dto == null) return;
        application.setApplicationStatus(dto.getApplicationStatus());
    }
}
