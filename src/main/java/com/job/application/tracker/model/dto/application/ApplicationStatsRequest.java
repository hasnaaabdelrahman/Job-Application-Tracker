package com.job.application.tracker.model.dto.application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationStatsRequest {
    private Long applications;
    private Long applied;
    private Long accepted;
    private Long interview;
    private Long rejected;
}
