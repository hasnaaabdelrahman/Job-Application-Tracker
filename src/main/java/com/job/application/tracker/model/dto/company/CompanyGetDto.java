package com.job.application.tracker.model.dto.company;

import com.job.application.tracker.model.dto.job.JobsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyGetDto {
    private Integer id;
    private String name;
    private List<JobsDto> jobs;
}
