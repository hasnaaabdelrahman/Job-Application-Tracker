package com.job.application.tracker.mapper;
import com.job.application.tracker.model.dto.ApplicationCreateDto;
import com.job.application.tracker.model.dto.ApplicationGetDto;
import com.job.application.tracker.model.dto.ApplicationUpdateDto;
import com.job.application.tracker.model.entity.Application;


public class ApplicationMapper {
    public static Application toEntity(ApplicationCreateDto dto) {
        if(dto == null) return null;
        Application application = new Application();
        application.setApplicationStatus(dto.getApplicationStatus());
        return application;
    }

    public static ApplicationGetDto toDto(Application application) {
        if (application == null) return null;
        ApplicationGetDto dto = new ApplicationGetDto();
        dto.setId(application.getId());
        dto.setApplicationStatus(application.getApplicationStatus());
        return dto;
    }

    public static void update(Application application , ApplicationUpdateDto dto) {
        if(application == null || dto == null) return;
        application.setApplicationStatus(dto.getApplicationStatus());
    }
}
