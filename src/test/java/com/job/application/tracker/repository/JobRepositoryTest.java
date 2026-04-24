package com.job.application.tracker.repository;

import com.job.application.tracker.model.entity.Company;
import com.job.application.tracker.model.entity.Job;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JobRepositoryTest {
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;

    private Job job;

    @BeforeEach
    void setUp() {
        Company company = new Company();
        company.setName("ABC Company");
        companyRepository.save(company);
        job = new Job(null, "full stack" , "We need a Full Stack Developer who can work on both frontend and backend technologies. You will build end-to-end features, manage databases, and ensure seamless integration between systems." , new ArrayList<>() ,company);
        jobRepository.save(job);
    }

    @Test
    public void findByCompanyId_shouldReturnTrue_whenCompanyIsFound() {
        List<Job> jobs = jobRepository.findByCompanyId(job.getCompany().getId());
        assertThat(jobs).isNotEmpty();
    }
    @Test
    public void findByCompanyId_shouldReturnEmpty_whenCompanyNotFound() {
        List<Job> jobs = jobRepository.findByCompanyId(999);
        assertThat(jobs).isEmpty();
    }


    @Test
    public void existsById_shouldReturnTrue_whenJobExists() {
        boolean exists = jobRepository.existsById(job.getId());
        assertThat(exists).isTrue();
    }

    @Test
    public void existsById_shouldReturnFalse_whenJobNotFound() {
        boolean exists = jobRepository.existsById(1000);
        assertThat(exists).isFalse();
    }
}
