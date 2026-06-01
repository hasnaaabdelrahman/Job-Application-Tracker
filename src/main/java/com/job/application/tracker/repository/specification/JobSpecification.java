package com.job.application.tracker.repository.specification;

import com.job.application.tracker.model.entity.Job;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class JobSpecification {
    public static Specification<Job> hasTitle(String title) {
        return (root , query , criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower( root.get("title")),
                        "%" + title.toLowerCase() + "%" );
    }

    public static Specification<Job> hasLocation(String location) {
        return (root , query , criteriaBuilder) ->
                criteriaBuilder.equal(root.get("location"),location);
    }

    public static Specification<Job> hasType(String type) {
        return (root , query , criteriaBuilder) ->
                criteriaBuilder.equal(root.get("type"),type);
    }
    public static Specification<Job> hasSalary(Long salary) {
        return (root , query , criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("salary"),salary);
    }
}
