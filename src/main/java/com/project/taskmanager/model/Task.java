package com.project.taskmanager.model;

import com.project.taskmanager.constant.ApplicationConstant.PRIORITY;
import com.project.taskmanager.constant.ApplicationConstant.STATUS;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "Task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(length = 20)
    private String title;

    @Column(length = 100)
    private String description;

    private LocalDate dueDate;

    private String category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "task_tags", joinColumns = @JoinColumn(name = "task_id"))
    @Column(name = "tag")
    private Set<String> tags = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private PRIORITY priority;

    @Enumerated(EnumType.STRING)
    private STATUS status;

    private LocalDate createdAt = LocalDate.now();

    private LocalDate updatedAt = LocalDate.now();
}
