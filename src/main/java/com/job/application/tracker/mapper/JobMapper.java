package com.job.application.tracker.mapper;

import com.job.application.tracker.dto.JobCreateDto;
import com.job.application.tracker.dto.JobGetDto;
import com.job.application.tracker.dto.JobUpdateDto;
import com.job.application.tracker.entity.Job;

public class JobMapper {

    public static Job toEntity(JobCreateDto dto) {
        if(dto == null) return null;
        Job job = new Job();
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
        return job;
    }

    public static JobGetDto toDto(Job job) {
        JobGetDto dto = new JobGetDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        return dto;
    }

    public static void update(Job job , JobUpdateDto dto) {
        if(job == null || dto == null) return;
        job.setTitle(dto.getTitle());
        job.setDescription(dto.getDescription());
    }
}
