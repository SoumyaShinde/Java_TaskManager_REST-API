package com.project.taskmanager.repository;

import com.project.taskmanager.dto.TaskRequest;
import com.project.taskmanager.model.Task;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification{
    public static Specification<Task> filterByCriteria(TaskRequest req) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(req.getStatus())) {
                predicates.add(cb.equal(cb.upper(root.get("status")), req.getStatus().toUpperCase()));
            }

            if (StringUtils.hasText(req.getPriority())) {
                predicates.add(cb.equal(cb.upper(root.get("priority")), req.getPriority().toUpperCase()));
            }

            if (StringUtils.hasText(req.getCategory())) {
                predicates.add(cb.equal(cb.lower(root.get("categories")), req.getCategory().toLowerCase()));
            }

            if (StringUtils.hasText(req.getTitle())) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + req.getTitle().toLowerCase() + "%"));
            }

            if (req.getTags() != null && !req.getTags().isEmpty()) {
                Join<Task, String> tagsJoin = root.join("tags");
                predicates.add(tagsJoin.in(req.getTags()));
                query.distinct(true); // To avoid duplicates
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}