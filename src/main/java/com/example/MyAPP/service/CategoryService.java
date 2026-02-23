package com.example.MyAPP.service;

import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    public String determineCategory(String title, String description) {
        String content = (title + " " + description).toLowerCase();

        if (content.contains("krishna") || content.contains("radha")) return "Krishna";
        if (content.contains("shiv") || content.contains("mahadev")) return "Shiv";
        if (content.contains("hanuman")) return "Hanuman";
        if (content.contains("ram") || content.contains("siya")) return "Ram";

        return "Devotional"; // Default
    }
}
