package com.example.MyAPP.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRegardingStreak {
    private String userId;
    private String mail;
    private String message;
}
