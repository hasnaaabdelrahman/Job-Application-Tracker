package com.job.application.tracker.service;

import com.job.application.tracker.dto.CompanyCreateDto;
import com.job.application.tracker.dto.CompanyGetDto;
import com.job.application.tracker.entity.Company;
import com.job.application.tracker.mapper.CompanyMapper;
import com.job.application.tracker.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServices {
    @Autowired
    CompanyRepository companyRepository;

    public CompanyGetDto add(CompanyCreateDto dto) {
        Company company = CompanyMapper.toEntity(dto);
         companyRepository.save(company);
         return CompanyMapper.toDto(company);
    }
    public List<Company> getAll() {
        return companyRepository.findAll();
    }
}
