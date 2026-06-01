package com.job.application.tracker.model.dto.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobStatsRequest {

    private Long totalJobs;
    private Long remoteJobs;
    private Long onsiteJobs;
    private Long hybridJobs;
}
