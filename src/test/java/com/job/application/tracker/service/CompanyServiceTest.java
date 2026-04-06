package com.job.application.tracker.service;

import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.repository.CompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;
    @InjectMocks
    CompanyService companyService;

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
