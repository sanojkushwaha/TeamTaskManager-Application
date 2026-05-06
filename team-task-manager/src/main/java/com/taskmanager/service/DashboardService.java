package com.taskmanager.service;

import com.taskmanager.dto.DashboardStats;
import com.taskmanager.dto.TaskResponse;
import com.taskmanager.enums.Role;
import com.taskmanager.enums.TaskStatus;
import com.taskmanager.model.Project;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.ProjectRepository;
import com.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    public DashboardStats getDashboardStats(User currentUser) {
        List<Project> projects;
        List<Task> allTasks;
        List<Task> overdueTasks;

        if (currentUser.getRole() == Role.ADMIN) {
            projects = projectRepository.findAll();
            allTasks = taskRepository.findAll();
            overdueTasks = taskRepository.findOverdueTasks(LocalDate.now());
        } else {
            projects = projectRepository.findProjectsForUser(currentUser);
            allTasks = taskRepository.findByAssignedTo(currentUser);
            overdueTasks = taskRepository.findOverdueTasksForUser(currentUser, LocalDate.now());
        }

        long todoCount = allTasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.TODO).count();
        long inProgressCount = allTasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS).count();
        long doneCount = allTasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.DONE).count();

        // Recent 5 tasks
        List<TaskResponse> recentTasks = allTasks.stream()
                .sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()))
                .limit(5)
                .map(taskService::mapToResponse)
                .collect(Collectors.toList());

        // Project-level stats
        List<DashboardStats.ProjectStats> projectStats = projects.stream()
                .map(p -> {
                    List<Task> pTasks = taskRepository.findByProject(p);
                    return DashboardStats.ProjectStats.builder()
                            .projectId(p.getId())
                            .projectName(p.getName())
                            .total(pTasks.size())
                            .todo(pTasks.stream().filter(t -> t.getStatus() == TaskStatus.TODO).count())
                            .inProgress(pTasks.stream().filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS).count())
                            .done(pTasks.stream().filter(t -> t.getStatus() == TaskStatus.DONE).count())
                            .build();
                })
                .collect(Collectors.toList());

        return DashboardStats.builder()
                .totalProjects(projects.size())
                .totalTasks(allTasks.size())
                .todoCount(todoCount)
                .inProgressCount(inProgressCount)
                .doneCount(doneCount)
                .overdueCount(overdueTasks.size())
                .recentTasks(recentTasks)
                .overdueTasks(overdueTasks.stream()
                        .map(taskService::mapToResponse).collect(Collectors.toList()))
                .projectStats(projectStats)
                .build();
    }
}
