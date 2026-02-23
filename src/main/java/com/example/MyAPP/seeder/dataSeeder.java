package com.example.MyAPP.seeder;

import com.example.MyAPP.entity.Bhajan;
import com.example.MyAPP.repository.BhajanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class dataSeeder implements CommandLineRunner {
    @Autowired
    private BhajanRepository bhajanRepository;

    @Override
    public void run(String... args) throws Exception {
        if(bhajanRepository.count()==0){
            List<Bhajan> initialBhajans= Arrays.asList(
                    new Bhajan("1", "vrindavan pyaro vrindavan", "Experience the divine beauty of Vrindavan with the video of “Pyaro Vrindavan” — a heartfelt tribute to love, devotion, and the eternal connection between the soul and the divine.\n" +
                            "\n" +
                            "Let the music transport you to the sacred lanes of Vrindavan, where every note echoes with bhakti and every frame radiates spiritual grace. This is more than just a song — it’s a journey into the heart of devotion.\n" +
                            "\n" +
                            "Singer - Indresh Upadhyay Ji\n" +
                            "Lyricist - Indresh Upadhyay Ji", "Shree Krishna", "/images/vrindavan_pyaro_vrindavan.jpeg","kNK7XYZcyBM","peace",0),
                    new Bhajan("2", "Hanuman Chalisa", "Gulshan Kumar", "lord Hanuman", "https://example.com/thumb2.jpg","AETFvQonfV8","aarti",0),
                    new Bhajan("3", "Ram aayenge", "Jubin Nautiyal", "Lord Rama", "https://example.com/thumb3.jpg","wncNcu6jEgs","peace",0),
                    new Bhajan("4", "Namami Shamishan", "Sachet Tandon", "Shiva", "https://example.com/thumb4.jpg","jh2LJVDtGIY","peace",0),
                    new Bhajan("5", "Radha ramanam hare hare", "With the divine blessings of Shri Radha Raman Lal Ji, Shri Indresh Ji is presenting his first composition ever, \n" +
                            "\"RADHA RAMANAM HARE HARE “\n" +
                            "______________________________________________________\n" +
                            "\n" +
                            "Voice -Shri Indresh Upadhyay Ji\n"+
                            "Lyrics & Composition - Shri Indresh Upadhyay Ji", "Shree Krishna", "/images/radha_ramanam_hare_hare.jpeg","CWcBWSKItk","peace",0),
                    new Bhajan("6", "Shiv Tandav Stotram", "Shankar Mahadevan", "Shiva", "https://example.com/thumb6.jpg","hMBKmQEPNzI","peace",0),
                    new Bhajan("7", "Gayatri Mantra", "Anuradha Paudwal", "Devi", "https://example.com/thumb7.jpg","rqStgo0Z_P0","peace",0)
            );
            bhajanRepository.saveAll(initialBhajans);
            log.info("data seeded");
        }else{
            log.info("data already present in database.skipping seeding");
        }
    }
}
