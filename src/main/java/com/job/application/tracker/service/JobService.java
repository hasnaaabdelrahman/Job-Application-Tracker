package com.job.application.tracker.service;

import com.job.application.tracker.model.dto.job.JobRequest;
import com.job.application.tracker.model.dto.job.JobResponse;
import com.job.application.tracker.model.dto.job.JobUpdateRequest;
import com.job.application.tracker.model.dto.job.JobsResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    JobResponse add(JobRequest job);
    List<JobsResponse> getAllByCompany(Pageable pageable, Integer id);
    JobResponse get(Integer id);
    List<JobsResponse> getByTitle(String title);
    List<JobResponse> showAll(Pageable pageable);
    void delete(Integer id);
    JobResponse update(Integer id , JobUpdateRequest dto);

}
