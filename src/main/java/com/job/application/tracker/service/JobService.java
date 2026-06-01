package com.job.application.tracker.service;

import com.job.application.tracker.model.dto.job.JobCreateDto;
import com.job.application.tracker.model.dto.job.JobGetDto;
import com.job.application.tracker.model.dto.job.JobUpdateDto;
import com.job.application.tracker.model.dto.job.JobsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    JobGetDto add(JobCreateDto job);
    List<JobsDto> getAllByCompany(Pageable pageable,Integer id);
    JobGetDto get(Integer id);
    List<JobsDto> getByTitle(String title);
    List<JobGetDto> showAll(Pageable pageable);
    void delete(Integer id);
    JobGetDto update(Integer id , JobUpdateDto dto);

}
