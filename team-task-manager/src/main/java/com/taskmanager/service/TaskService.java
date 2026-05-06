package com.taskmanager.service;

import com.taskmanager.dto.TaskRequest;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.enums.Role;
import com.taskmanager.enums.TaskStatus;
import com.taskmanager.model.Project;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.ProjectMemberRepository;
import com.taskmanager.repository.ProjectRepository;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMemberRepository projectMemberRepository;

    // ---- Create Task ----
    public TaskResponse createTask(TaskRequest request, User currentUser) {
        Project project = findProjectOrThrow(request.getProjectId());
        checkProjectAccess(project, currentUser);

        User assignedTo = null;
        if (request.getAssignedToUserId() != null) {
            assignedTo = userRepository.findById(request.getAssignedToUserId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
        }

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.TODO)
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .project(project)
                .assignedTo(assignedTo)
                .createdBy(currentUser)
                .build();

        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    // ---- Get all tasks for a project ----
    public List<TaskResponse> getTasksByProject(Long projectId, User currentUser) {
        Project project = findProjectOrThrow(projectId);
        checkProjectAccess(project, currentUser);

        return taskRepository.findByProject(project)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // ---- Get tasks assigned to me ----
    public List<TaskResponse> getMyTasks(User currentUser) {
        return taskRepository.findByAssignedTo(currentUser)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // ---- Get task by ID ----
    public TaskResponse getTaskById(Long taskId, User currentUser) {
        Task task = findTaskOrThrow(taskId);
        checkProjectAccess(task.getProject(), currentUser);
        return mapToResponse(task);
    }

    // ---- Update task ----
    public TaskResponse updateTask(Long taskId, TaskRequest request, User currentUser) {
        Task task = findTaskOrThrow(taskId);
        checkProjectAccess(task.getProject(), currentUser);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());

        if (request.getAssignedToUserId() != null) {
            User assignedTo = userRepository.findById(request.getAssignedToUserId())
                    .orElseThrow(() -> new RuntimeException("Assigned user not found"));
            task.setAssignedTo(assignedTo);
        }

        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    // ---- Update task status only ----
    public TaskResponse updateTaskStatus(Long taskId, TaskStatus status, User currentUser) {
        Task task = findTaskOrThrow(taskId);
        checkProjectAccess(task.getProject(), currentUser);
        task.setStatus(status);
        task = taskRepository.save(task);
        return mapToResponse(task);
    }

    // ---- Delete task ----
    public void deleteTask(Long taskId, User currentUser) {
        Task task = findTaskOrThrow(taskId);

        if (currentUser.getRole() != Role.ADMIN &&
            !task.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to delete this task");
        }

        taskRepository.delete(task);
    }

    // ---- Get all tasks (Admin) ----
    public List<TaskResponse> getAllTasks(User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only Admins can view all tasks");
        }
        return taskRepository.findAll()
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // ---- Helpers ----
    private Project findProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found: " + id));
    }

    private Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found: " + id));
    }

    private void checkProjectAccess(Project project, User user) {
        if (user.getRole() == Role.ADMIN) return;
        boolean isMember = projectMemberRepository.existsByProjectAndUser(project, user);
        if (!isMember) throw new RuntimeException("Access denied to this project");
    }

    public TaskResponse mapToResponse(Task task) {
        boolean isOverdue = task.getDueDate() != null
                && task.getDueDate().isBefore(LocalDate.now())
                && task.getStatus() != TaskStatus.DONE;

        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .overdue(isOverdue)
                .projectId(task.getProject().getId())
                .projectName(task.getProject().getName())
                .assignedToId(task.getAssignedTo() != null ? task.getAssignedTo().getId() : null)
                .assignedToName(task.getAssignedTo() != null ? task.getAssignedTo().getName() : null)
                .createdById(task.getCreatedBy().getId())
                .createdByName(task.getCreatedBy().getName())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
