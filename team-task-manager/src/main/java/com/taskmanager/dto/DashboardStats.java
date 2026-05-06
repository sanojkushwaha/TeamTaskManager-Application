package com.taskmanager.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardStats {
    private long totalProjects;
    private long totalTasks;
    private long todoCount;
    private long inProgressCount;
    private long doneCount;
    private long overdueCount;
    private List<TaskResponse> recentTasks;
    private List<TaskResponse> overdueTasks;
    private List<ProjectStats> projectStats;

    @Data
    @Builder
    public static class ProjectStats {
        private Long projectId;
        private String projectName;
        private long total;
        private long todo;
        private long inProgress;
        private long done;
    }
}
