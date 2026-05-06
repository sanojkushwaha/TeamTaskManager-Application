package com.taskmanager.dto;

import com.taskmanager.enums.Priority;
import com.taskmanager.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDate dueDate;
    private boolean overdue;
    private Long projectId;
    private String projectName;
    private Long assignedToId;
    private String assignedToName;
    private Long createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
