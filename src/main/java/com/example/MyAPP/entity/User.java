package com.example.MyAPP.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Document(collection = "USERS")
@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String mail;
    private List<String> roles=new ArrayList<>();
    @DBRef
    private List<Bhajan> myFavorites=new ArrayList<>();

    private String alarmTime;
    private String alarmPreference;

    private int streak;
    private int pendingStreak;
    private LocalDate challengeStart;
    private boolean inRecoveryMode;

    private LocalDate lastActivityDate;

    private Map<String, Boolean> dailySadhana = new HashMap<>();



}
