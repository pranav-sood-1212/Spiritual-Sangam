package com.example.MyAPP.dto;

import com.example.MyAPP.entity.Bhajan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user_activities")
public class ActivityLogDto {
    private String username;
    private String bhajanId;
    private String action;

}
