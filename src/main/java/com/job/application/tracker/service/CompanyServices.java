package com.job.application.tracker.service;

import com.job.application.tracker.dto.CompanyCreateDto;
import com.job.application.tracker.dto.CompanyGetDto;
import com.job.application.tracker.dto.CompanyUpdateDto;
import com.job.application.tracker.dto.JobsDto;
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
    public List<CompanyGetDto> getAll() {
        return companyRepository.findAll()
                .stream()
                .map(company -> new CompanyGetDto(company.getId(), company.getName() ,company.getJobs()
                        .stream()
                        .map(job -> new JobsDto(job.getId() , job.getTitle() , job.getDescription()))
                        .toList()))
                .toList();
    }
    public CompanyGetDto get(Integer id) {
        Company company = companyRepository.findById(id).orElseThrow();
        return CompanyMapper.toDto(company);
    }

    public CompanyGetDto update(Integer id , CompanyUpdateDto dto) {
        Company company = companyRepository.findById(id).orElseThrow();
        CompanyMapper.update(company,dto);
        companyRepository.save(company);
        return CompanyMapper.toDto(company);
    }
    public void delete(Integer id) {
        companyRepository.deleteById(id);
    }
}
