package com.job.application.tracker.service;

import com.job.application.tracker.dto.CompanyCreateDto;
import com.job.application.tracker.dto.CompanyGetDto;
import com.job.application.tracker.dto.CompanyUpdateDto;
import com.job.application.tracker.dto.JobsDto;
import com.job.application.tracker.entity.Company;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.mapper.CompanyMapper;
import com.job.application.tracker.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CompanyService implements ICompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyGetDto add(CompanyCreateDto dto) {
        Company company = CompanyMapper.toEntity(dto);
         companyRepository.save(company);
         return CompanyMapper.toDto(company);
    }

    @Override
    public List<CompanyGetDto> getAll(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .stream()
                .map(company -> new CompanyGetDto(company.getId(), company.getName() ,company.getJobs()
                        .stream()
                        .map(job -> new JobsDto(job.getId() , job.getTitle() , job.getDescription()))
                        .toList()))
                .toList();
    }

    @Override
    public CompanyGetDto get(Integer id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found with id: "+ id));
        return CompanyMapper.toDto(company);
    }

    @Override
    public CompanyGetDto update(Integer id , CompanyUpdateDto dto) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found with id: "+ id));
        CompanyMapper.update(company,dto);
        companyRepository.save(company);
        return CompanyMapper.toDto(company);
    }

    @Override
    public void delete(Integer id) {
        if(!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Company not found with id: "+ id);
        }
        companyRepository.deleteById(id);
    }
}
