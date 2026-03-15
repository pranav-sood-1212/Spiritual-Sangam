package com.example.MyAPP.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MoodMappingService {

    public List<String> determineMoods(String title, String description) {
        String content = (Optional.ofNullable(title).orElse("") + " " +
                Optional.ofNullable(description).orElse("")).toLowerCase();

        List<String> activeMoods = new ArrayList<>();

        // 1. BRAHMA MUHURTA (4 AM - 6 AM Vibe)
        if (content.contains("brahma muhurta") || content.contains("अमृतवेला") ||
                content.contains("amrit vela") || content.contains("4am") || content.contains("5am")) {
            activeMoods.add("Brahma Muhurta");
        }

        // 2. MORNING AARTIS
        if (content.contains("morning") || content.contains("subah") || content.contains("सुबह") ||
                content.contains("pratah") || content.contains("suprabhatam") || content.contains("aarti") ||
                content.contains("chalisa") || content.contains("आरती")) {
            activeMoods.add("Morning Aartis");
        }

        // 3. EVENING AARTIS
        if (content.contains("evening") || content.contains("shaam") || content.contains("शाम") ||
                content.contains("sandhya") || content.contains("sunset") || content.contains("deepam")) {
            activeMoods.add("Evening Aartis");
        }

        // 4. MANTRA CHANTING
        if (content.contains("mantra") || content.contains("मंत्र") || content.contains("jaap") ||
                content.contains("108") || content.contains("dhun") || content.contains("chant") ||
                content.contains("loop")) {
            activeMoods.add("Mantra Chanting");
        }

        // 5. ENERGETIC VIBE
        if (content.contains("energetic") || content.contains("tandav") || content.contains("shakti") ||
                content.contains("josh") || content.contains("dance") || content.contains("nach") ||
                content.contains("dj") || content.contains("remix")) {
            activeMoods.add("Energetic Vibe");
        }

        // 6. PEACEFUL VIBE
        if (content.contains("peace") || content.contains("calm") || content.contains("dhyan") ||
                content.contains("sukoon") || content.contains("relax") || content.contains("flute") ||
                content.contains("lofi") || content.contains("healing")) {
            activeMoods.add("Peaceful Vibe");
        }

        // Fallback: If no mood is detected, give it a general tag
        if (activeMoods.isEmpty()) {
            activeMoods.add("Devotional");
        }

        return activeMoods;
    }
}