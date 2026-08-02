package com.sangam.connect.controller;

import com.sangam.connect.DTO.MessageDto;
import com.sangam.connect.Requests.MessageRequest;
import com.sangam.connect.entity.MessageEntity;
import com.sangam.connect.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
public class ChatController {

    private final MessageRepo messageRepo;
    private final SimpMessagingTemplate simpMessagingTemplate;
    public ChatController(MessageRepo messageRepo, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageRepo = messageRepo;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat/{communityId}")
    public void processMessage(@Payload MessageRequest messageRequest, @DestinationVariable String communityId){ // payload -> spring automatically converts raw json to java obj
        MessageEntity messageEntity=new MessageEntity(communityId,messageRequest.getUserId(), messageRequest.getUserName(), messageRequest.getContent(), messageRequest.getMessageType());
        MessageEntity savedMessage =messageRepo.save(messageEntity);
        String destination="/topic/community/"+communityId;
        simpMessagingTemplate.convertAndSend(destination,savedMessage);
    }


}
