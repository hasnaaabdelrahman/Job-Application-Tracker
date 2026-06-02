package com.job.application.tracker.repository;

import com.job.application.tracker.common.ApplicationStatus;
import com.job.application.tracker.model.dto.application.*;
import com.job.application.tracker.model.entity.Application;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application , Integer> {
    List<Application> findByApplicationStatus(ApplicationStatus status);
    boolean existsByUserIdAndJobId(Integer userId , Integer jobId);
    List<Application> findByUserId(Integer userId);
    boolean existsById(Integer id);
    List<Application> findByJobCompanyIdAndUserId(Integer companyId, Integer userId);
    List<Application> findByApplicationStatusAndUserId(ApplicationStatus status,Integer userId);
    List<Application> findByUser_Id(Integer userId ,Pageable page);
    Optional<Application> findByUser_IdAndId(Integer userId , Integer applicationId);
    List<Application> findByJob_Company_Id(Integer companyId);

    @Query(
            """
            SELECT new com.job.application.tracker.model.dto.application.ApplicationsCountRequest(
            COUNT(a)
            )
            FROM Application a
            WHERE a.job.id =:id
            """
    )

    ApplicationsCountRequest countApplicationsByJobId(@Param("id") Integer id);


    @Query(
            """
            SELECT com.job.application.tracker.model.dto.application.ApplicationAcceptedStatusCount(
            COUNT(a)
            )
            FROM Application a
            WHERE a.applicationStatus = com.job.application.tracker.common.ApplicationStatus.ACCEPTED
            AND
            a.user.id = :id
            """
    )
    ApplicationAcceptedStatusCount countAcceptedApplications(@Param("id") Integer id);


    @Query(
            """
            SELECT com.job.application.tracker.model.dto.application.ApplicationAppliedStatusCount(
            COUNT(a)
            )
            FROM Application a
            WHERE a.applicationStatus = com.job.application.tracker.common.ApplicationStatus.APPLIED
            AND
            a.user.id = :id
            """
    )
    ApplicationAppliedStatusCount countAppliedApplications(@Param("id") Integer id);


    @Query(
            """
            SELECT com.job.application.tracker.model.dto.application.ApplicationInterviewStatusCount(
            COUNT(a)
            )
            FROM Application a
            WHERE a.applicationStatus = com.job.application.tracker.common.ApplicationStatus.INTERVIEW
            AND
            a.user.id = :id
            """
    )
    ApplicationInterviewStatusCount countInterviewApplications(@Param("id") Integer id);

    @Query(
            """
            SELECT com.job.application.tracker.model.dto.application.ApplicationRejectedStatusCount(
            COUNT(a)
            )
            FROM Application a
            WHERE a.applicationStatus = com.job.application.tracker.common.ApplicationStatus.REJECTED
            AND
            a.user.id = :id
            """
    )
    ApplicationRejectedStatusCount countRejectedApplications(@Param("id") Integer id);

}
