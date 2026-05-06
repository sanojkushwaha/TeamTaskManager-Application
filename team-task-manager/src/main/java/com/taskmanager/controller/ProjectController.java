package com.taskmanager.controller;

import com.taskmanager.dto.ProjectRequest;
import com.taskmanager.dto.ProjectResponse;
import com.taskmanager.model.User;
import com.taskmanager.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * POST /api/projects
     * Create a new project (Admin only)
     */
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.createProject(request, currentUser));
    }

    /**
     * GET /api/projects
     * Get all projects accessible to current user
     */
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getMyProjects(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(projectService.getMyProjects(currentUser));
    }

    /**
     * GET /api/projects/{id}
     * Get project by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(projectService.getProjectById(id, currentUser));
    }

    /**
     * PUT /api/projects/{id}
     * Update project (Admin only)
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectRequest request,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(projectService.updateProject(id, request, currentUser));
    }

    /**
     * DELETE /api/projects/{id}
     * Delete project (Admin only)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProject(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        projectService.deleteProject(id, currentUser);
        return ResponseEntity.ok(Map.of("message", "Project deleted successfully"));
    }

    /**
     * POST /api/projects/{id}/members/{userId}
     * Add member to project (Admin only)
     */
    @PostMapping("/{id}/members/{userId}")
    public ResponseEntity<Map<String, String>> addMember(
            @PathVariable Long id,
            @PathVariable Long userId,
            @AuthenticationPrincipal User currentUser) {
        projectService.addMember(id, userId, currentUser);
        return ResponseEntity.ok(Map.of("message", "Member added successfully"));
    }

    /**
     * DELETE /api/projects/{id}/members/{userId}
     * Remove member from project (Admin only)
     */
    @DeleteMapping("/{id}/members/{userId}")
    public ResponseEntity<Map<String, String>> removeMember(
            @PathVariable Long id,
            @PathVariable Long userId,
            @AuthenticationPrincipal User currentUser) {
        projectService.removeMember(id, userId, currentUser);
        return ResponseEntity.ok(Map.of("message", "Member removed successfully"));
    }

    /**
     * GET /api/projects/users
     * Get all users (for assignment dropdowns)
     */
    @GetMapping("/users/all")
    public ResponseEntity<?> getAllUsers(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(projectService.getAllUsers().stream()
                .map(u -> Map.of("id", u.getId(), "name", u.getName(),
                                 "email", u.getEmail(), "role", u.getRole()))
                .toList());
    }
}
