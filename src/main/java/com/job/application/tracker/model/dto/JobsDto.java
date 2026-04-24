package com.job.application.tracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobsDto {
    private Integer id;
    private String title;
    private String description;
}
