package com.example.MyAPP.repository;


import com.example.MyAPP.entity.Bhajan;
import com.example.MyAPP.enums.SourceType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BhajanRepository extends MongoRepository<Bhajan,String> {
    List<Bhajan> findByTitleContainingIgnoreCaseOrDeityContainingIgnoreCaseOrArtistNameContainingIgnoreCase(
            String title,String deity,String singer
    );
    List<Bhajan> findByMood(String mood); /// peace anger etc
    List<Bhajan> findTop10ByOrderByLikesDesc();
    List<Bhajan> findTop10BySourceTypeOrderByLikesDesc(SourceType sourceType);
//    List<Bhajan> findByCategoryIgnoreCase(String category); /// poem,song
    List<Bhajan> findByDeityIgnoreCaseOrderByLikesDesc(String deityName); /// lord krishna lord shiva etc
    List<Bhajan> findBySourceType(SourceType source); /// local or YouTube music

    List<Bhajan> findBySourceTypeAndTitleContainingIgnoreCase(SourceType sourceType, String query);
}
