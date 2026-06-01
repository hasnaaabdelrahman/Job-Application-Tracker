package com.job.application.tracker.mapper;

import com.job.application.tracker.model.dto.job.JobRequest;
import com.job.application.tracker.model.dto.job.JobResponse;
import com.job.application.tracker.model.dto.job.JobUpdateRequest;
import com.job.application.tracker.model.dto.job.JobsResponse;
import com.job.application.tracker.model.entity.Job;

public class JobMapper {

    public static Job toEntity(JobRequest dto) {
        if(dto == null) return null;
        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setSalary(dto.getSalary());
        job.setType(dto.getType());
        return job;
    }

    public static Job toResponse(JobsResponse dto) {
        if(dto == null) return null;
        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setSalary(dto.getSalary());
        job.setType(dto.getType());
        return job;
    }

    public static JobResponse toDto(Job job) {
        JobResponse dto = new JobResponse();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setLocation(job.getLocation());
        dto.setSalary(job.getSalary());
        dto.setType(job.getType());
        dto.setCompany_id(job.getCompany().getId());
        return dto;
    }

    public static void update(Job job , JobUpdateRequest dto) {
        if(job == null || dto == null) return;
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        job.setLocation(dto.getLocation());
        job.setSalary(dto.getSalary());
        job.setType(dto.getType());
    }
}
