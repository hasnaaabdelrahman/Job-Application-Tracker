package com.job.application.tracker.model.dto.job;

import com.job.application.tracker.common.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
    private Integer id;
    private String title;
    private String description;
    private Integer company_id;
    private Long salary;
    private String location;
    private JobType type;
}
