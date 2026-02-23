package com.example.MyAPP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDashboardData {
    private String username;
    private int currentStreak;
    private boolean isStreakAtRisk;
    private boolean inRecoveryMode;
    private String todaySignificance; // This MUST match the frontend field name
}