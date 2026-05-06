package com.taskmanager.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private String createdByName;
    private Long createdById;
    private LocalDateTime createdAt;
    private int memberCount;
    private int taskCount;
    private List<MemberInfo> members;

    @Data
    @Builder
    public static class MemberInfo {
        private Long userId;
        private String name;
        private String email;
    }
}
