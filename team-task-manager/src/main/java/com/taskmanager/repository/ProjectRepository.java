package com.taskmanager.repository;

import com.taskmanager.model.Project;
import com.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCreatedBy(User user);

    // Projects where user is a member OR created by user
    @Query("SELECT DISTINCT p FROM Project p LEFT JOIN p.members m " +
           "WHERE p.createdBy = :user OR m.user = :user")
    List<Project> findProjectsForUser(@Param("user") User user);
}
