package com.job.application.tracker.service;

import com.job.application.tracker.dto.CompanyCreateDto;
import com.job.application.tracker.dto.CompanyGetDto;
import com.job.application.tracker.dto.CompanyUpdateDto;

import java.util.List;

public interface ICompanyService {
    CompanyGetDto add(CompanyCreateDto dto);
    List<CompanyGetDto> getAll();
    CompanyGetDto get(Integer id);
    CompanyGetDto update(Integer id , CompanyUpdateDto dto);
    void delete(Integer id);
}
