package com.job.application.tracker.service;

import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {
    @Mock
    JobRepository jobRepository;
    @InjectMocks
    JobService jobService;

    @Test
    void deleteJob_shouldThrowException_whenJobIsNotFound() {
        when(jobRepository.existsById(999)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class , ()->
                jobService.delete(999));
    }

    @Test
    void deleteJob_shouldCallDelete_whenJobIsFound() {
        when(jobRepository.existsById(1)).thenReturn(true);
        jobService.delete(1);
        verify(jobRepository).deleteById(1);
    }
}
