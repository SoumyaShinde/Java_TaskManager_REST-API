package com.project.taskmanager.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class TaskRequest {
        @NotBlank(message = "Title is mandatory")
        @Size(max = 20, message = "Title can be max 20 characters")
        private String title;

        @Size(max = 100, message = "Description can be max 100 characters")
        private String description;

        @FutureOrPresent(message = "Due date must be today or in the future")
        private LocalDate dueDate;

        @NotBlank(message = "Category is mandatory")
        private String category;

        private Set<String> tags;

        @NotBlank(message = "Priority is mandatory")
        private String priority;

        @NotBlank(message = "Status is mandatory")
        private String status;
}
