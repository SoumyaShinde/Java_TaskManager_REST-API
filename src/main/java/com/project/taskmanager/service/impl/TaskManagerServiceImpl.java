package com.project.taskmanager.service.impl;

import com.project.taskmanager.dto.TaskRequest;
import com.project.taskmanager.dto.CompletedResponse;
import com.project.taskmanager.constant.ApplicationConstant;
import com.project.taskmanager.constant.ExceptionMessages;
import com.project.taskmanager.exception.ResourceNotFoundException;
import com.project.taskmanager.model.Task;
import com.project.taskmanager.repository.TaskRepository;
import com.project.taskmanager.repository.TaskSpecification;
import com.project.taskmanager.service.TaskManagerService;
import com.project.taskmanager.util.TaskManagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskManagerServiceImpl implements TaskManagerService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskManagerUtil taskManagerUtil;

    @Override
    public Task createTask(TaskRequest task) throws IllegalArgumentException {
        taskManagerUtil.validateDates(task);
        Task taskModel = taskManagerUtil.mapToTaskModel(task);
        taskRepository.save(taskModel);
        return taskModel;
    }

    @Override
    public Page<Task> getAllTaskPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return taskRepository.findAll(pageable);
    }

    @Override
    public Optional<Task> getSpecificTask(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public List<Task> upcomingTasksDue(int days) {
        LocalDate now = LocalDate.now();
        LocalDate target = now.plusDays(days);
        List<Task> upcoming = taskRepository.findByDueDateBetween(now, target);
        PriorityQueue<Task> queue = new PriorityQueue<>(Comparator.comparing(Task::getDueDate));
        queue.addAll(upcoming);

        List<Task> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            sortedList.add(queue.poll());
        }

        return sortedList;
    }

    @Override
    public List<Task> getDueTodayTasks() {
        return taskRepository.findAllByDueDate(LocalDate.now());
    }

    @Override
    public Task updateTask(Long id, TaskRequest task) {
        Task fetchTask = taskRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(ExceptionMessages.TASK_NOT_FOUND));
        return taskRepository.save(taskManagerUtil.updateExistingTask(fetchTask, task));
    }

    @Override
    public void deleteTaskById(Long id) {
        if (!taskRepository.existsById(id))
            throw new IllegalArgumentException(ExceptionMessages.TASK_NOT_FOUND);
        taskRepository.deleteById(id);
    }

    @Override
    public Map<String, List<String>> groupByCategory() {
        List<Task> taskAll = taskRepository.findAll();
        if(taskAll.isEmpty())
            throw new IllegalArgumentException(ExceptionMessages.NO_TASK_FOR_CATEGORY);
        return taskAll.stream().filter(task -> task.getCategory()!=null && task.getTitle()!=null).
                collect(Collectors.groupingBy(Task::getCategory, Collectors.mapping(Task::getTitle, Collectors.toList())));
    }

    @Override
    public List<Task> getTaskByTag(Set<String> tags) {
        List<Task> matchingTasks = taskRepository.findByTagsIn(tags);
        return matchingTasks.stream()
                .filter(task -> task.getTags().containsAll(tags)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> filterTask(TaskRequest taskRequest) {
        Specification<Task> taskByFilter = TaskSpecification.filterByCriteria(taskRequest);
        List<Task> tasks = taskRepository.findAll(taskByFilter);
        if (tasks.isEmpty()) {
            throw new IllegalArgumentException(ExceptionMessages.DATA_NOT_FOUND);
        }
        return ResponseEntity.ok(tasks);
    }

    @Override
    public List<Task> getExpiredTask() {
        return taskRepository.findByDueDateBeforeAndStatusNot(LocalDate.now(), ApplicationConstant.STATUS.DONE);
    }

    @Override
    public List<CompletedResponse> completedTask() {
        List<Task> task = taskRepository.findAllByStatus(ApplicationConstant.STATUS.DONE);
        return task.stream().map(TaskManagerUtil::completedTaskResponse).collect(Collectors.toList());
    }

}
