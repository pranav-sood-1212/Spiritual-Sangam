package com.sangam.connect.repository;

import com.sangam.connect.entity.CommunityEntity;
import com.sangam.connect.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommunityRepo extends JpaRepository<CommunityEntity, String> {
    boolean existsByCommunityIdAndMembers_UserName(String communityId, String userName);

    boolean existsByCommunityName(String communityName);

    List<CommunityEntity> findByCommunityNameContainingIgnoreCase(String query);
}
