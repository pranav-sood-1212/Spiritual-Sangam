package com.example.MyAPP.service;

import com.example.MyAPP.dto.ActivityLogDto;
import com.example.MyAPP.dto.NotificationRegardingStreak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {


    @Autowired
    private MailNotificationService mailNotificationService;


    @KafkaListener(topics = "bhajan-logs",groupId = "bhajan-group")
    public void consume(ActivityLogDto activityLogDto){
        System.out.println("data consumed");
        System.out.println("user:" + activityLogDto.getUsername());
    }

    @KafkaListener(topics = "streak-notifications",groupId = "bhajan-group")
    public void consumeForStreakNotify(NotificationRegardingStreak notificationRegardingStreak){
        mailNotificationService.sendMail(notificationRegardingStreak.getMail(),"pranavsood888@gmail.com",notificationRegardingStreak.getMessage());

    }
}
