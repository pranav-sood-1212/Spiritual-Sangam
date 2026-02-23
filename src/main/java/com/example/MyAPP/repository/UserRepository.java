package com.example.MyAPP.repository;

import com.example.MyAPP.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
    List<User> findByLastActivityDate(LocalDate date);
}
