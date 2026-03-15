package com.example.MyAPP.entity;

import com.example.MyAPP.enums.SourceType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data // Logic: Includes Getter, Setter, Equals, HashCode, and RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "BHAJANS")
public class Bhajan implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id; // This is the _id in MongoDB
    private String title;
    private String artistName;
    private String lyrics;
    private String thumbnail;
    private String videoIdOrUrl;
    private int likes;

    // categorization

    private String deity;
    private List<String> mood;
    private SourceType sourceType;  // local or youtube
//    private String category; // poem or song or katha
}