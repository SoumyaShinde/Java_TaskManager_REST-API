package com.project.taskmanager.service;

import com.project.taskmanager.dto.CompletedResponse;
import com.project.taskmanager.dto.TaskRequest;
import com.project.taskmanager.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TaskManagerService {
    Task createTask(TaskRequest task);

    Page<Task> getAllTaskPaginated(int page, int size);

    Optional<Task> getSpecificTask(Long id);

    List<Task> upcomingTasksDue(int days);

    List<Task> getDueTodayTasks();

    Task updateTask(Long id, TaskRequest task);

    void deleteTaskById(Long id);

    Map<String, List<String>> groupByCategory();

    List<Task> getTaskByTag(Set<String> tags);

    ResponseEntity<?> filterTask(TaskRequest task);

    List<Task> getExpiredTask();

    List<CompletedResponse> completedTask();

}
