package com.example.MyAPP.repository;


import com.example.MyAPP.entity.Bhajan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BhajanRepository extends MongoRepository<Bhajan,String> {
    List<Bhajan> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(
            String title,String category,String singer
    );
    List<Bhajan> findTop3ByOrderByLikesDesc();
    List<Bhajan> findByMood(String mood);

    List<Bhajan> findByCategoryIgnoreCase(String deityName);
}
