package com.project.taskmanager.controller;

import com.project.taskmanager.dto.CompletedResponse;
import com.project.taskmanager.dto.Status;
import com.project.taskmanager.dto.TaskRequest;
import com.project.taskmanager.constant.ExceptionMessages;
import com.project.taskmanager.exception.ResourceNotFoundException;
import com.project.taskmanager.model.Task;
import com.project.taskmanager.service.TaskManagerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class TaskManagerController {

    @Autowired
    private TaskManagerService taskManagerService;

    @PostMapping("/tasks")
    public ResponseEntity<?> addTasks(@Valid @RequestBody TaskRequest task){
        Task created = taskManagerService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/tasks")
    public ResponseEntity<?> updateTask(@RequestParam("id") Long id, @RequestBody TaskRequest task) {
        Task updatedTask = taskManagerService.updateTask(id, task);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }

    @GetMapping("/tasks")
    public ResponseEntity<?> getTasks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        return ResponseEntity.status(HttpStatus.OK).body(taskManagerService.getAllTaskPaginated(page, size));
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> retrieveSpecificTask(@PathVariable("id") Long id){
        Task specialTask = taskManagerService.getSpecificTask(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.TASK_NOT_FOUND));
        return ResponseEntity.status(HttpStatus.OK).body(specialTask);
    }

    @GetMapping("/tasks/upcoming")
    public ResponseEntity<?> upcomingTasks(@RequestParam("days") int days) {
        List<Task> upcomingTasks = taskManagerService.upcomingTasksDue(days);
        if(!upcomingTasks.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(upcomingTasks);
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new Status(HttpStatus.NO_CONTENT.toString(), String.format(ExceptionMessages.NO_TASKS_DUE, days, (days > 1 ? "days" : "day"))));
        }
    }

    @GetMapping("/tasks/dueToday")
    public ResponseEntity<?> dueTodayTasks(){
        List<Task> dueTodayTask = taskManagerService.getDueTodayTasks();
        if(!dueTodayTask.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(dueTodayTask);
        else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new Status(HttpStatus.NO_CONTENT.toString(), ExceptionMessages.NO_TASKS_DUE_TODAY));
        }
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") Long id){
        taskManagerService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ExceptionMessages.TASK_DELETED_SUCCESSFULLY);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getDetailsByCategory(){
        Map<String, List<String>> categoryByTask = taskManagerService.groupByCategory();
        return ResponseEntity.status(HttpStatus.OK).body(categoryByTask);
    }

    @GetMapping("/tasks/tags")
    public ResponseEntity<?> getTags(@RequestParam("tags") Set<String> tags) {
        List<Task> filterTask = taskManagerService.getTaskByTag(tags);
        return ResponseEntity.status(HttpStatus.OK).body(filterTask);
    }

    @PostMapping("/tasks/filtering")
    public ResponseEntity<?> responseFilter(@RequestBody TaskRequest task){
        ResponseEntity<?> response = taskManagerService.filterTask(task);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/expiredTasks")
    public ResponseEntity<?> getDueDatesdata(){
        return ResponseEntity.ok(taskManagerService.getExpiredTask());
    }

    @GetMapping("/completedTasks")
    public ResponseEntity<?> completedTask(){
        return ResponseEntity.ok(taskManagerService.completedTask());
    }
}
