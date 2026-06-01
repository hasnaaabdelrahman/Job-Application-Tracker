package com.job.application.tracker.model.dto.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDashboardRequest {
    private Long jobsPosted;
    private Long applicationsReceived;
}
