package com.job.application.tracker.service;

import com.job.application.tracker.model.dto.CompanyCreateDto;
import com.job.application.tracker.model.dto.CompanyGetDto;
import com.job.application.tracker.model.dto.CompanyUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICompanyService {
    CompanyGetDto add(CompanyCreateDto dto);
    List<CompanyGetDto> getAll(Pageable pageable);
    CompanyGetDto get(Integer id);
    CompanyGetDto update(Integer id , CompanyUpdateDto dto);
    void delete(Integer id);
}
