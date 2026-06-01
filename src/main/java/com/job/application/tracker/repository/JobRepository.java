package com.job.application.tracker.repository;

import com.job.application.tracker.model.dto.job.*;
import com.job.application.tracker.model.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> , JpaSpecificationExecutor<Job> {
    List<Job> findByCompanyId(Integer companyId);
    List<Job> findByTitleContainingIgnoreCase(String title);
    boolean existsById(Integer id);

    @Query(
            """
            SELECT new com.job.application.tracker.model.dto.job.JobApplicationsRequest(
            COUNT(a))
            FROM Application a
            
            WHERE a.job.company.id = :id
            """
    )
    JobApplicationsRequest countApplicationsByCompanyId(@Param("id") Integer id);

    @Query(
            """
            SELECT new com.job.application.tracker.model.dto.job.JobCountRequest (
            COUNT(j)
            )
            FROM Job j
            """
    )
    JobCountRequest countAllJobs();

    @Query(
            """
            SELECT new com.job.application.tracker.model.dto.job.JobRemoteRequest (
            COUNT(j)
            )
            FROM Job j
            WHERE j.type =  com.job.application.tracker.common.JobType.REMOTE
            """
    )
    JobRemoteRequest countRemoteJobs();


    @Query(
            """
            SELECT new com.job.application.tracker.model.dto.job.JobHybridRequest (
            COUNT(j)
            )
            FROM Job j
            WHERE j.type =  com.job.application.tracker.common.JobType.HYBRID

            """
    )
    JobHybridRequest countHybridJobs();

    @Query(
            """
            SELECT new com.job.application.tracker.model.dto.job.JobOnsiteRequest (
            COUNT(j)
            )
            FROM Job j
            WHERE j.type =  com.job.application.tracker.common.JobType.ONSITE
            """
    )
    JobOnsiteRequest countOnsiteJobs();

    @Query(
            """
            SELECT  new com.job.application.tracker.model.dto.job.JobsResponse (
            j.id,
            j.title,
            j.description,
            j.salary,
            j.location,
            j.type
            )
            FROM Job j
            """
    )
    List<JobsResponse> findTop10ByOrderByIdDesc();
}
