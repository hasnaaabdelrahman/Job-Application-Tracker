package com.job.application.tracker.service;

import com.job.application.tracker.model.entity.Company;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyService companyService;


    @Test
    void  getCompany_shouldCallGet_whenCompanyExists() {
        Company company = new Company(1, "tech", new ArrayList<>());
        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        companyService.get(1);
        verify(companyRepository).findById(1);
    }

    @Test
    void getCompany_shouldThrowException_whenCompanyNotExists() {
        Company company = new Company(1, "tech", new ArrayList<>());

        when(companyRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                companyService.get(999)
        );

        verify(companyRepository).findById(999);

    }


    @Test
    void deleteCompany_shouldThrowException_whenCompanyIsNotFound() {
        when(companyRepository.existsById(999)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, ()->
                companyService.delete(999));
    }

    @Test
    void deleteCompany_shouldCallDelete_whenCompanyIsFound() {
        when(companyRepository.existsById(1)).thenReturn(true);
        companyService.delete(1);
        verify(companyRepository).deleteById(1);
    }

}
