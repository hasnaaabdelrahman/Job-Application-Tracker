package com.job.application.tracker.dto;

import com.job.application.tracker.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationByStatusDto {
    private Integer id;
    private Application.ApplicationStatus applicationStatus;

}
