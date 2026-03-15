package com.example.MyAPP.service;

import com.example.MyAPP.dto.ActivityLogDto;
import com.example.MyAPP.dto.NotificationRegardingStreak;
import com.example.MyAPP.entity.User;
import com.example.MyAPP.repository.ActivityRepository;
import com.example.MyAPP.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailNotificationService mailNotificationService;

    public void sendActivityLog(String username, String bhajanId, String action) {
        ActivityLogDto activityLogDto = new ActivityLogDto(username, bhajanId, action);
        try {
            // Key = username, Value = activityLogDto
            // We try to send, but we wrap it in a 'shield'
            kafkaTemplate.send("bhajan-logs", username, activityLogDto)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            System.err.println("Background error: " + ex.getMessage());
                            activityRepository.save(activityLogDto);
                        }
                    });
        } catch (Exception e) {
            // THIS IS THE KEY: If Kafka is down, we catch the error here
            // and prevent it from crashing the API.
            System.err.println("KAFKA IS DOWN: Could not even initialize send. Skipping log.");
            activityRepository.save(activityLogDto);
            // OPTIONAL: You can save this to MongoDB here as a backup!
        }
    }

    public void sendNotificationForStreakSave(User user, String message){
        NotificationRegardingStreak notificationRegardingStreak=new NotificationRegardingStreak(user.getId(),user.getMail(),message);
        try {
            kafkaTemplate.send("streak-notifications", user.getId(), notificationRegardingStreak);
        }catch (Exception e){
            System.err.println("KAFKA IS DOWN: Could not even initialize send. Skipping log.");
            mailNotificationService.sendMail(user.getMail(),"pranavsood888@gmail.com",message);
        }
    }
}
