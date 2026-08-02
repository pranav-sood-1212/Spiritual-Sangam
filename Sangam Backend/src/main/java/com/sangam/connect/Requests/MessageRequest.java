package com.sangam.connect.Requests;

import com.mongodb.lang.NonNull;
import com.sangam.connect.enums.MessageType;

import java.util.UUID;

public class MessageRequest {
    private UUID userId;
    private String content;
    private MessageType messageType;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
