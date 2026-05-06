package com.taskmanager.controller;

import com.taskmanager.dto.DashboardStats;
import com.taskmanager.model.User;
import com.taskmanager.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    /**
     * GET /api/dashboard
     * Returns full dashboard stats for current user
     * Admins see everything, Members see their own
     */
    @GetMapping
    public ResponseEntity<DashboardStats> getDashboard(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(dashboardService.getDashboardStats(currentUser));
    }
}
