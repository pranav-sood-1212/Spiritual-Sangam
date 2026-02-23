package com.example.MyAPP.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter@Data // Logic: Includes Getter, Setter, Equals, HashCode, and RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "BHAJANS")
public class Bhajan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String bhajanId; // This is the _id in MongoDB
    private String title;
    private String description;
    private String category;
    private String thumbnail;
    private String videoId;
    private String mood;
    private int likes;
}