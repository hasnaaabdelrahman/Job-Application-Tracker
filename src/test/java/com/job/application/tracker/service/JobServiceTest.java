package com.job.application.tracker.service;

import com.job.application.tracker.entity.Company;
import com.job.application.tracker.entity.Job;
import com.job.application.tracker.entity.User;
import com.job.application.tracker.exceptions.ResourceNotFoundException;
import com.job.application.tracker.repository.JobRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

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
    void  getJob_shouldCallGet_whenJobExists() {
        Company company = new Company();
        company.setName("ABC Company");
        Job job = new Job(1, "full stack" , "We need a Full Stack Developer who can work on both frontend and backend technologies. You will build end-to-end features, manage databases, and ensure seamless integration between systems." , new ArrayList<>() ,company);
        when(jobRepository.findById(1)).thenReturn(Optional.of(job));
        jobService.get(1);
        verify(jobRepository).findById(1);
    }

    @Test
    void  getJob_shouldThrowException_whenJobNotExists() {
        Company company = new Company();
        company.setName("ABC Company");
        Job job = new Job(1, "full stack" , "We need a Full Stack Developer who can work on both frontend and backend technologies. You will build end-to-end features, manage databases, and ensure seamless integration between systems." , new ArrayList<>() ,company);
        when(jobRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class , ()->
                jobService.get(999));
    }

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
