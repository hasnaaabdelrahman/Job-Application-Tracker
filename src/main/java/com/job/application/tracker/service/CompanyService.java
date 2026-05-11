package com.job.application.tracker.service;

import com.job.application.tracker.model.dto.company.CompanyCreateDto;
import com.job.application.tracker.model.dto.company.CompanyGetDto;
import com.job.application.tracker.model.dto.company.CompanyUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    CompanyGetDto add(CompanyCreateDto dto);
    List<CompanyGetDto> getAll(Pageable pageable);
    CompanyGetDto get(Integer id);
    CompanyGetDto update(Integer id , CompanyUpdateDto dto);
    void delete(Integer id);
}
