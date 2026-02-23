package com.example.MyAPP.service;

import org.springframework.stereotype.Service;

@Service
public class MoodMappingService {
     public String determineMood(String title, String description){
        String content = ( (title != null ? title : "") + " " + (description != null ? description : "") ).toLowerCase();

        // morning aartis
        if(content.contains("morning") || content.contains("subah") || content.contains("सुबह") ||
                content.contains("pratah") || content.contains("प्रातः") ||
                content.contains("amrit vela") || content.contains("अमृतवेला") ||
                content.contains("dawn") || content.contains("suprabhatam") || content.contains("सुप्रभातम") ||
                content.contains("surya") || content.contains("सूर्य")||
                content.contains("aarti") || content.contains("आरती") ||
                content.contains("chalisa") || content.contains("चालीसा") ||
                content.contains("puja") || content.contains("पूजा")
        )return "morning aartis";


        // evening aartis
        if(content.contains("evening") || content.contains("shaam") || content.contains("शाम") ||
                content.contains("sandhya") || content.contains("सन्ध्या") || content.contains("संध्या") ||
                content.contains("sunset") || content.contains("sayankal") || content.contains("सायंकाल") ||
                content.contains("shayan") || content.contains("शयन") || // Bedtime/Closing
                content.contains("deepam") || content.contains("दीपक")||
                content.contains("aarti") || content.contains("आरती") ||
                content.contains("stotram") || content.contains("स्तोत्रम") ||
                content.contains("ashtak") || content.contains("अष्टक")
        ) return "evening aartis";

        // mantra chantings
        if (content.contains("mantra") || content.contains("मंत्र") || content.contains("मन्त्र") ||
                content.contains("chant") || content.contains("jaap") || content.contains("जाप") ||
                content.contains("108") || content.contains("1008") || // Typical repetition counts
                content.contains("times") || content.contains("repetition") ||
                content.contains("loop") || content.contains("continuous") ||

                // Specific chanting styles
                content.contains("dhun") || content.contains("धुन") ||
                content.contains("shloka") || content.contains("श्लोक") ||
                content.contains("stotra") || content.contains("स्तोत्र") ||
                content.contains("japa") || content.contains("तप") ||

                // Seed sounds (Bija Mantras)
                content.contains(" om ") || content.contains(" ॐ ") ||
                content.contains("namah") || content.contains("नमः")
        ) return "mantra_chanting";



        // energetic
        if (content.contains("energetic") || content.contains("power") || content.contains("fast") ||
                content.contains("tandav") || content.contains("तांडव") || content.contains("ताण्डव") ||
                content.contains("shakti") || content.contains("शक्ति") ||
                content.contains("josh") || content.contains("जोश") ||

                // Instrument based (Percussive/Loud)
                content.contains("drum") || content.contains("dhol") || content.contains("ढोल") ||
                content.contains("damaru") || content.contains("डमरू") ||
                content.contains("shankh") || content.contains("शंख") ||
                content.contains("nagada") || content.contains("नगाड़ा") ||

                // Type based (Celebratory/Vigorous)
                content.contains("dance") || content.contains("nach") || content.contains("नाच") ||
                content.contains("celebration") || content.contains("utsav") || content.contains("उत्सव") ||
                content.contains("remix") || content.contains("dj") || content.contains("trance") ||
                content.contains("rock") || content.contains("vibrant") ||
                content.contains("loud") || content.contains("heavy") ||

                // Specific powerful terms
                content.contains("hanuman chalisa") && content.contains("powerful") ||
                content.contains("mahakal") || content.contains("महाकाल") ||
                content.contains("aghori") || content.contains("अघोरी")
        ) return "energetic";




        //peaceful
        if (content.contains("peace") || content.contains("shant") || content.contains("शान्त") ||
                content.contains("calm") || content.contains("dhyan") || content.contains("ध्यान") ||
                content.contains("relax") || content.contains("heal") || content.contains("sooth") ||
                content.contains("soulful") || content.contains("sukoon") || content.contains("सुकून") ||

                //instrument based
                content.contains("flute") || content.contains("sitar") || content.contains("बांसुरी") ||
                content.contains("instrumental") || content.contains("soft") ||

                //type based
                content.contains("slow") || content.contains("lofi") || content.contains("ambient") ||
                content.contains("acoustic") || content.contains("unplugged") || content.contains("sleep") ||
                content.contains("subah") || content.contains("सुबह")||


                content.contains("stress") || content.contains("relief") || content.contains("anxiety") ||
                content.contains("tension") || content.contains("mukti") || content.contains("मुक्ति") ||
                content.contains("healing") || content.contains("therapy") || content.contains("wellness") ||
                content.contains("calming") || content.contains("comfort") || content.contains("sooth") ||

                // Emotional/Devotional Comfort
                content.contains("dukh") || content.contains("दुःख") || content.contains("dard") ||
                content.contains("vairagya") || content.contains("वैराग्य") ||
                content.contains("sharan") || content.contains("शरण") || // Refuge
                content.contains("kripa") || content.contains("कृपा") || // Grace
                content.contains("bhakti") || content.contains("भक्ति") ||

                // Sound Therapy based
                content.contains("solfeggio") || content.contains("frequency") ||
                content.contains("hz") || content.contains("432hz") || // Common healing frequency
                content.contains("binaural") || content.contains("nature") ||
                content.contains("river") || content.contains("rain") ||
                content.contains("om") || content.contains("ॐ") ||

                // Specific calming types
                content.contains("deep sleep") || content.contains("nidra") || content.contains("निद्रा") ||
                content.contains("positive") || content.contains("sakaaratmak") || content.contains("सकारात्मक") ||
                content.contains("shanti") || content.contains("शान्ति")
        ) return "peaceful";

        return "";
    }

}
