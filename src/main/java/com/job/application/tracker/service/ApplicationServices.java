package com.job.application.tracker.service;

import com.job.application.tracker.entity.Application;
import com.job.application.tracker.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServices {
    @Autowired
    ApplicationRepository applicationRepository;

    public Application add(Application application) {
        return applicationRepository.save(application);
    }
    public List<Application> get() {
        return applicationRepository.findAll();
    }
    public Application update(Application application) {
        return applicationRepository.save(application);
    }
    public void delete(Application application) {
        if (application.getApplicationStatus() == Application.ApplicationStatus.REJECTED) {
            applicationRepository.delete(application);
        }
    }

}
