package com.example.MyAPP.service;

import org.springframework.stereotype.Service;

@Service
public class DeityService {
    public String determineCategory(String title, String description) {
        // We keep it lowercase for English, but Hindi characters are unaffected by .toLowerCase()
        String content = (title + " " + description).toLowerCase();

        // 1. Krishna Logic: Includes Kanha, Radhe, and Devanagari
        if (content.contains("krishn") || content.contains("radh") || content.contains("vrindavan") ||
                content.contains("kanha") || content.contains("radhe") ||
                content.contains("कृष्ण") || content.contains("राधा") || content.contains("कान्हा")) {
            return "Krishna";
        }

        // 2. Shiv Logic: Includes Bhole, Shambhu, and Devanagari
        if (content.contains("shiv") || content.contains("mahadev") || content.contains("bhol") ||
                content.contains("shambhu") || content.contains("shankar") ||
                content.contains("शिव") || content.contains("महादेव") || content.contains("भोले")) {
            return "Shiva";
        }

        // 3. Hanuman Logic: Includes Bajrangbali and Devanagari
        if (content.contains("hanuman") || content.contains("bajrang") || content.contains("sankat mochan") ||
                content.contains("maruti") ||
                content.contains("हनुमान") || content.contains("बजरंग")) {
            return "Hanuman";
        }

        // 4. Ram Logic: Includes Siya, Sita, and Devanagari
        // Using a regex check for "ram" to avoid matching "pramod" or "program"
        if (content.matches(".*\\bram\\b.*") || content.contains("siya") || content.contains("sita") ||
                content.contains("राम") || content.contains("सिया") || content.contains("सीता")) {
            return "Ram";
        }


        return "Devotional";
    }
}