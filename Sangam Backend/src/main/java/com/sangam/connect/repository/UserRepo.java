package com.sangam.connect.repository;

import com.sangam.connect.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface UserRepo extends JpaRepository<UserEntity, UUID> {
    UserEntity findByPhoneNumberHash(String phnNo);
    Optional<UserEntity> findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);


    boolean existsByPhoneNumberHash(String phoneNumber);
}
