package com.job.application.tracker.mapper;

import com.job.application.tracker.model.dto.CompanyCreateDto;
import com.job.application.tracker.model.dto.CompanyGetDto;
import com.job.application.tracker.model.dto.CompanyUpdateDto;
import com.job.application.tracker.model.dto.JobsDto;
import com.job.application.tracker.model.entity.Company;

public class CompanyMapper {

    public static Company toEntity(CompanyCreateDto dto) {
        if(dto == null) return null;
        Company company = new Company();
        company.setName(dto.getName());
        return company;
    }

    public static CompanyGetDto toDto(Company company) {
        if(company == null) return null;
        CompanyGetDto dto = new CompanyGetDto();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setJobs(company.getJobs()
                .stream()
                .map(job -> new JobsDto(job.getId(), job.getTitle() , job.getDescription()))
                .toList());
        return dto;
    }

    public static void update(Company company , CompanyUpdateDto dto) {
        if(company == null || dto == null) return;
        company.setName(dto.getName());
    }
}
