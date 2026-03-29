package com.job.application.tracker.service;

import com.job.application.tracker.entity.Company;
import com.job.application.tracker.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServices {
    @Autowired
    CompanyRepository companyRepository;

    public Company add(Company company) {
        return companyRepository.save(company);
    }
    public List<Company> getAll() {
        return companyRepository.findAll();
    }
}
