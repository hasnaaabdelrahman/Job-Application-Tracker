package com.job.application.tracker.model.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobsResponse {
    private Integer id;
    private String title;
    private String description;
}
