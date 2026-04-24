package com.job.application.tracker.service;

import com.job.application.tracker.model.entity.Application;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {
    @Mock
    ApplicationRepository applicationRepository;
    @InjectMocks
    ApplicationService applicationService;

    @Test
    void deleteApplication_shouldCallDelete_whenApplicationIsFound() {
        when(applicationRepository.findById(1)).thenReturn(Optional.of(new Application()));
        applicationService.delete(1);
        verify(applicationRepository).deleteById(1);

    }

    @Test
    void deleteApplication_shouldThrowException_whenApplicationIsNotFound() {
        when(applicationRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                ()-> applicationService.delete(99));
    }

}
