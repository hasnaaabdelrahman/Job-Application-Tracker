package com.job.application.tracker.mapper;

import com.job.application.tracker.model.dto.company.CompanyRequest;
import com.job.application.tracker.model.dto.company.CompanyResponse;
import com.job.application.tracker.model.dto.company.CompanyUpdateRequest;
import com.job.application.tracker.model.dto.job.JobsResponse;
import com.job.application.tracker.model.entity.Company;

public class CompanyMapper {

    public static Company toEntity(CompanyRequest dto) {
        if(dto == null) return null;
        Company company = new Company();
        company.setName(dto.getName());
        return company;
    }

    public static CompanyResponse toDto(Company company) {
        if(company == null) return null;
        CompanyResponse dto = new CompanyResponse();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setJobs(company.getJobs()
                .stream()
                .map(job -> new JobsResponse(job.getId(), job.getTitle() , job.getDescription() ,
                        job.getSalary() , job.getLocation() , job.getType()))
                .toList());
        return dto;
    }

    public static void update(Company company , CompanyUpdateRequest dto) {
        if(company == null || dto == null) return;
        company.setName(dto.getName());
    }
}
