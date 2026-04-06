package com.job.application.tracker.repository;

import com.job.application.tracker.entity.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;

@DataJpaTest
public class CompanyRepositoryTest
{
    @Autowired
    private CompanyRepository companyRepository;
    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company(null , "tech" , new ArrayList<>());
       companyRepository.save(company);

    }

    @Test
    public void existsById_returnTrue_whenCompanyIsExists() {
        boolean exists = companyRepository.existsById(company.getId());
        assertThat(exists).isTrue();
    }

    @Test
    public void existsById_returnTrue_whenCompanyIsNotExists() {
        boolean exists = companyRepository.existsById(999);
        assertThat(exists).isFalse();
    }
}
