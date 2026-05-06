package com.taskmanager.dto;

import com.taskmanager.enums.Priority;
import com.taskmanager.enums.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {
    @NotBlank(message = "Task title is required")
    private String title;

    private String description;

    private TaskStatus status;

    private Priority priority;

    private LocalDate dueDate;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    private Long assignedToUserId;
}
