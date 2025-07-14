package com.project.taskmanager.repository;

import com.project.taskmanager.constant.ApplicationConstant;
import com.project.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> , JpaSpecificationExecutor<Task> {

    List<Task> findAllByDueDate(LocalDate now);

    List<Task> findByDueDateBetween(LocalDate now, LocalDate target);

    List<Task> findByTagsIn(Set<String> tags);

    List<Task> findByDueDateBeforeAndStatusNot(LocalDate now, ApplicationConstant.STATUS status);

    List<Task> findAllByStatus(ApplicationConstant.STATUS status);
}
