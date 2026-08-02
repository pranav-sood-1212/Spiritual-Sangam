package com.sangam.connect.repository;

import com.sangam.connect.entity.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface MessageRepo extends MongoRepository<MessageEntity,String> {
    MessageEntity findByContent(String content);
    List<MessageEntity> findByCommunityIdOrderByMessageTimeAsc(String communityId);
}
