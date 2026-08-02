package com.sangam.connect.DTO;

import com.mongodb.lang.Nullable;
import com.sangam.connect.enums.MessageType;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.UUID;


@Controller
public class MessageDto {
    private String communityId;
    private UUID userId;
    private String userName;
    private String content;
    private MessageType messageType;
    private Instant messageTime=Instant.now();
}
