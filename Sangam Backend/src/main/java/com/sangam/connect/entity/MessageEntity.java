package com.sangam.connect.entity;

import com.mongodb.lang.NonNull;
import com.mongodb.lang.Nullable;
import com.sangam.connect.enums.MessageType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.UUID;

@Document(collection = "messages")
public class MessageEntity {

    @Id
    private String messageId;
    @NonNull
    private String communityId;
    @NonNull
    private UUID userId;
    private String userName;
    private String content;
    private MessageType messageType;
    @CreatedDate
    private Instant messageTime;

    public MessageEntity() {
        // Left empty intentionally for Spring Data reflection mapping
    }

    public MessageEntity(@NonNull String communityId, @NonNull UUID userId, String userName, String content, MessageType messageType) {
        this.communityId = communityId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.messageType = messageType;
//        this.messageTime = messageTime;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @NonNull
    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(@NonNull String communityId) {
        this.communityId = communityId;
    }

    @NonNull
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(@NonNull UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Instant getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Instant messageTime) {
        this.messageTime = messageTime;
    }
}