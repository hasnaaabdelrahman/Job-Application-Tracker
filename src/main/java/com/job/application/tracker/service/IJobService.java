package com.job.application.tracker.service;

import com.job.application.tracker.dto.JobCreateDto;
import com.job.application.tracker.dto.JobGetDto;
import com.job.application.tracker.dto.JobUpdateDto;
import com.job.application.tracker.dto.JobsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IJobService {

    JobGetDto add(JobCreateDto job);
    List<JobsDto> getAllByCompany(Integer id);
    JobGetDto get(Integer id);
    List<JobsDto> getByTitle(String title);
    List<JobGetDto> showAll(Pageable pageable);
    void delete(Integer id);
    JobGetDto update(Integer id , JobUpdateDto dto);

}
