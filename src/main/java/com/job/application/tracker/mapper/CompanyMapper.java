package com.job.application.tracker.mapper;

import com.job.application.tracker.dto.CompanyCreateDto;
import com.job.application.tracker.dto.CompanyGetDto;
import com.job.application.tracker.dto.CompanyUpdateDto;
import com.job.application.tracker.dto.JobUpdateDto;
import com.job.application.tracker.entity.Company;
import com.job.application.tracker.entity.Job;

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
        return dto;
    }

    public static void update(Company company , CompanyUpdateDto dto) {
        if(company == null || dto == null) return;
        company.setName(dto.getName());
    }
}
