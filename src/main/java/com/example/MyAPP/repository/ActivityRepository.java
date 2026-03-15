package com.example.MyAPP.repository;


import com.example.MyAPP.dto.ActivityLogDto;
import com.example.MyAPP.entity.Bhajan;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ActivityRepository extends MongoRepository<ActivityLogDto,String> {

}
