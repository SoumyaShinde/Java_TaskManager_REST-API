package com.project.taskmanager.util;

import com.project.taskmanager.constant.ApplicationConstant;
import com.project.taskmanager.constant.ExceptionMessages;
import com.project.taskmanager.dto.CompletedResponse;
import com.project.taskmanager.dto.TaskRequest;
import com.project.taskmanager.model.Task;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class TaskManagerUtil {

    public void validateDates(TaskRequest task) {
        LocalDate currentDate = LocalDate.now();
        if (task.getDueDate().isBefore(currentDate))
            throw new IllegalArgumentException(ExceptionMessages.DUE_DATE_BEFORE_CURRENT);
    }

    public Task updateExistingTask(Task existingTask, TaskRequest newUpdate) {
        if (StringUtils.hasText(newUpdate.getTitle()))
            existingTask.setTitle(newUpdate.getTitle());
        if (StringUtils.hasText(newUpdate.getDescription()))
            existingTask.setDescription(newUpdate.getDescription());
        if (StringUtils.hasText(newUpdate.getCategory()))
            existingTask.setCategory(newUpdate.getCategory());
        if (newUpdate.getTags() != null && !newUpdate.getTags().isEmpty())
            existingTask.setTags(new HashSet<>(newUpdate.getTags()));
        if (newUpdate.getPriority() != null)
            existingTask.setPriority(parsePriority(newUpdate.getPriority()));
        if (newUpdate.getStatus() != null)
            existingTask.setStatus(parseStatus(newUpdate.getStatus()));
        existingTask.setUpdatedAt(LocalDate.now());

        return existingTask;
    }

    public Task mapToTaskModel(TaskRequest task) throws IllegalArgumentException {
        Task taskModel = new Task();
        taskModel.setTitle(task.getTitle());
        taskModel.setDescription(task.getDescription());
        try {
            if (task.getDueDate()!=null)
                taskModel.setDueDate(task.getDueDate());
        } catch (DateTimeParseException e) {
            throw new RuntimeException(ExceptionMessages.INCORRECT_DATE_FORMAT);
        }
        taskModel.setCategory(task.getCategory());
        if (task.getTags() != null) {
            taskModel.setTags(new HashSet<>(task.getTags()));
        }
        taskModel.setPriority(parsePriority(task.getPriority()));
        taskModel.setStatus(parseStatus(task.getStatus()));
        return taskModel;
    }

    private ApplicationConstant.PRIORITY parsePriority(String priority) {
        try {
            return ApplicationConstant.PRIORITY.valueOf(priority.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_PRIORITY
                    + Arrays.toString(ApplicationConstant.PRIORITY.values()));
        }
    }

    private ApplicationConstant.STATUS parseStatus(String status) {
        try {
            return ApplicationConstant.STATUS.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_STATUS
                    + Arrays.toString(ApplicationConstant.STATUS.values()));
        }
    }

    public static CompletedResponse completedTaskResponse(Task task) {
        return new CompletedResponse(task.getId(), task.getTitle(), task.getDescription());
    }
}
