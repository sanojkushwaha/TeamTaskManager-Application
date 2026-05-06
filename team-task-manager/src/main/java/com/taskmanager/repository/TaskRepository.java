package com.taskmanager.repository;

import com.taskmanager.enums.TaskStatus;
import com.taskmanager.model.Project;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProject(Project project);

    List<Task> findByAssignedTo(User user);

    List<Task> findByProjectAndStatus(Project project, TaskStatus status);

    // All tasks assigned to user across all projects
    List<Task> findByAssignedToAndStatus(User user, TaskStatus status);

    // Overdue tasks: due date passed and not done
    @Query("SELECT t FROM Task t WHERE t.dueDate < :today AND t.status != 'DONE'")
    List<Task> findOverdueTasks(@Param("today") LocalDate today);

    // Overdue tasks for a specific user
    @Query("SELECT t FROM Task t WHERE t.assignedTo = :user AND t.dueDate < :today AND t.status != 'DONE'")
    List<Task> findOverdueTasksForUser(@Param("user") User user, @Param("today") LocalDate today);

    // Count by status
    long countByProjectAndStatus(Project project, TaskStatus status);

    // Count all tasks for project
    long countByProject(Project project);
}
