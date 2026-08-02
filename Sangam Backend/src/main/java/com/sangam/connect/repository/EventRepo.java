package com.sangam.connect.repository;

import com.sangam.connect.entity.EventEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;


public interface EventRepo extends MongoRepository<EventEntity,String> {
    List<EventEntity> findByExpectedDateAfter(LocalDate now);
}
