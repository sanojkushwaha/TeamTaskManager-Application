package com.taskmanager.service;

import com.taskmanager.dto.ProjectRequest;
import com.taskmanager.dto.ProjectResponse;
import com.taskmanager.enums.Role;
import com.taskmanager.model.Project;
import com.taskmanager.model.ProjectMember;
import com.taskmanager.model.User;
import com.taskmanager.repository.ProjectMemberRepository;
import com.taskmanager.repository.ProjectRepository;
import com.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final UserRepository userRepository;

    // ---- Create Project (Admin only) ----
    public ProjectResponse createProject(ProjectRequest request, User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only Admins can create projects");
        }

        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .createdBy(currentUser)
                .build();

        project = projectRepository.save(project);
        return mapToResponse(project);
    }

    // ---- Get all projects for current user ----
    public List<ProjectResponse> getMyProjects(User currentUser) {
        List<Project> projects;
        if (currentUser.getRole() == Role.ADMIN) {
            projects = projectRepository.findAll();
        } else {
            projects = projectRepository.findProjectsForUser(currentUser);
        }
        return projects.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // ---- Get single project ----
    public ProjectResponse getProjectById(Long projectId, User currentUser) {
        Project project = findProjectOrThrow(projectId);
        checkAccess(project, currentUser);
        return mapToResponse(project);
    }

    // ---- Update project (Admin only) ----
    public ProjectResponse updateProject(Long projectId, ProjectRequest request, User currentUser) {
        Project project = findProjectOrThrow(projectId);

        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only Admins can update projects");
        }

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project = projectRepository.save(project);
        return mapToResponse(project);
    }

    // ---- Delete project (Admin only) ----
    @Transactional
    public void deleteProject(Long projectId, User currentUser) {
        Project project = findProjectOrThrow(projectId);

        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only Admins can delete projects");
        }

        projectRepository.delete(project);
    }

    // ---- Add member to project ----
    @Transactional
    public void addMember(Long projectId, Long userId, User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only Admins can add members");
        }

        Project project = findProjectOrThrow(projectId);
        User userToAdd = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        if (projectMemberRepository.existsByProjectAndUser(project, userToAdd)) {
            throw new RuntimeException("User is already a member of this project");
        }

        ProjectMember member = ProjectMember.builder()
                .project(project)
                .user(userToAdd)
                .build();

        projectMemberRepository.save(member);
    }

    // ---- Remove member from project ----
    @Transactional
    public void removeMember(Long projectId, Long userId, User currentUser) {
        if (currentUser.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only Admins can remove members");
        }

        Project project = findProjectOrThrow(projectId);
        User userToRemove = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        projectMemberRepository.deleteByProjectAndUser(project, userToRemove);
    }

    // ---- Get all users (for assigning) ----
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ---- Helpers ----
    private Project findProjectOrThrow(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found: " + id));
    }

    private void checkAccess(Project project, User user) {
        if (user.getRole() == Role.ADMIN) return;
        boolean isMember = projectMemberRepository.existsByProjectAndUser(project, user);
        if (!isMember) {
            throw new RuntimeException("Access denied to project: " + project.getId());
        }
    }

    public ProjectResponse mapToResponse(Project project) {
        List<ProjectMember> members = projectMemberRepository.findByProject(project);

        List<ProjectResponse.MemberInfo> memberInfos = members.stream()
                .map(m -> ProjectResponse.MemberInfo.builder()
                        .userId(m.getUser().getId())
                        .name(m.getUser().getName())
                        .email(m.getUser().getEmail())
                        .build())
                .collect(Collectors.toList());

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .createdByName(project.getCreatedBy().getName())
                .createdById(project.getCreatedBy().getId())
                .createdAt(project.getCreatedAt())
                .memberCount(members.size())
                .taskCount(project.getTasks().size())
                .members(memberInfos)
                .build();
    }
}
