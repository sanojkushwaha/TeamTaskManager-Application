package com.taskmanager.controller;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.enums.TaskStatus;
import com.taskmanager.model.User;
import com.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * POST /api/tasks
     * Create a new task
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(request, currentUser));
    }

    /**
     * GET /api/tasks/project/{projectId}
     * Get all tasks for a project
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskResponse>> getTasksByProject(
            @PathVariable Long projectId,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(taskService.getTasksByProject(projectId, currentUser));
    }

    /**
     * GET /api/tasks/my
     * Get tasks assigned to current user
     */
    @GetMapping("/my")
    public ResponseEntity<List<TaskResponse>> getMyTasks(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(taskService.getMyTasks(currentUser));
    }

    /**
     * GET /api/tasks/all
     * Get all tasks (Admin only)
     */
    @GetMapping("/all")
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(taskService.getAllTasks(currentUser));
    }

    /**
     * GET /api/tasks/{id}
     * Get task by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTask(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(taskService.getTaskById(id, currentUser));
    }

    /**
     * PUT /api/tasks/{id}
     * Update a task
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(taskService.updateTask(id, request, currentUser));
    }

    /**
     * PATCH /api/tasks/{id}/status
     * Update task status only (TODO / IN_PROGRESS / DONE)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal User currentUser) {
        TaskStatus status = TaskStatus.valueOf(body.get("status").toUpperCase());
        return ResponseEntity.ok(taskService.updateTaskStatus(id, status, currentUser));
    }

    /**
     * DELETE /api/tasks/{id}
     * Delete a task
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        taskService.deleteTask(id, currentUser);
        return ResponseEntity.ok(Map.of("message", "Task deleted successfully"));
    }
}
