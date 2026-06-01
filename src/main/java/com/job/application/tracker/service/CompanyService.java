package com.job.application.tracker.service;

import com.job.application.tracker.model.dto.company.CompanyRequest;
import com.job.application.tracker.model.dto.company.CompanyResponse;
import com.job.application.tracker.model.dto.company.CompanyUpdateRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    CompanyResponse add(CompanyRequest dto);
    List<CompanyResponse> getAll(Pageable pageable);
    CompanyResponse get(Integer id);
    CompanyResponse update(Integer id , CompanyUpdateRequest dto);
    void delete(Integer id);
}
