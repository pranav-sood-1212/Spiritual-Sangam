package com.example.MyAPP.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SadhanaLogDto {
    private String username;
    private String ritualName; // e.g., "Japa", "Reading"
    private boolean completed;
}
