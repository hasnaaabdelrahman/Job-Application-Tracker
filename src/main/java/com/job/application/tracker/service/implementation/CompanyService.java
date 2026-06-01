package com.job.application.tracker.service.implementation;

import com.job.application.tracker.model.dto.company.CompanyDashboardRequest;
import com.job.application.tracker.model.dto.company.CompanyRequest;
import com.job.application.tracker.model.dto.company.CompanyResponse;
import com.job.application.tracker.model.dto.company.CompanyUpdateRequest;
import com.job.application.tracker.model.dto.job.JobsDto;
import com.job.application.tracker.model.entity.Company;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.mapper.CompanyMapper;
import com.job.application.tracker.repository.CompanyRepository;
import com.job.application.tracker.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements com.job.application.tracker.service.CompanyService {

    private final CompanyRepository companyRepository;
    private final JobService jobService;
    private final JobRepository jobRepository;

    @Override
    public CompanyResponse add(CompanyRequest dto) {
        Company company = CompanyMapper.toEntity(dto);
         companyRepository.save(company);
         return CompanyMapper.toDto(company);
    }

    @Override
    public List<CompanyResponse> getAll(Pageable pageable) {
        return companyRepository.findAll(pageable)
                .stream()
                .map(company -> new CompanyResponse(company.getId(), company.getName() ,company.getJobs()
                        .stream()
                        .map(job -> new JobsDto(job.getId() , job.getTitle() , job.getDescription()))
                        .toList()))
                .toList();
    }

    @Override
    public CompanyResponse get(Integer id) {
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found with id: "+ id));
        return CompanyMapper.toDto(company);
    }

    @Override
    public CompanyResponse update(Integer id , CompanyUpdateRequest dto) {
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

    public CompanyDashboardRequest dashboard(Integer id) {
        if(!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Company not found with id: "+ id);
        }
        return  CompanyDashboardRequest.builder()
                .jobsPosted((long) jobService.getAllByCompany(id).size())
                .applicationsReceived(jobService.countApplications(id).getApplications())
                .build();
    }
}
