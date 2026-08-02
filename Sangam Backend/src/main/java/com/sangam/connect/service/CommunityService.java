package com.sangam.connect.service;

import com.sangam.connect.DTO.CommunitiesResponseDTO;
import com.sangam.connect.DTO.CommunityDetailsDTO;
import com.sangam.connect.Requests.CreateCommunityRequest;
import com.sangam.connect.entity.CommunityEntity;
import com.sangam.connect.entity.MessageEntity;
import com.sangam.connect.entity.UserEntity;
import com.sangam.connect.repository.CommunityRepo;
import com.sangam.connect.repository.MessageRepo;
import com.sangam.connect.repository.UserRepo;
import com.sangam.connect.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommunityService {
    private final UserRepo userRepo;
    private final CommunityRepo communityRepo;
    private final MessageRepo messageRepo;


    public CommunityService(UserRepo userRepo, CommunityRepo communityRepo, MessageRepo messageRepo) {
        this.userRepo = userRepo;
        this.communityRepo = communityRepo;
        this.messageRepo = messageRepo;
    }
    @Transactional
    public boolean create(CreateCommunityRequest request, CustomUserDetails customUserDetails) {
        String hostName= customUserDetails.getUsername();

        if (communityRepo.existsById(request.getCommunityId())) {
            throw new RuntimeException("Community ID already exists");
        }

        if (communityRepo.existsByCommunityName(request.getCommunityName())) {
            throw new RuntimeException("Community name already exists");
        }

        CommunityEntity community = new CommunityEntity(
                request.getCommunityId(),
                request.getCommunityName(),
                hostName,
                request.getCommunityDescriptionDescription()
        );

        communityRepo.save(community);
        return true;
    }
    @Transactional
    public boolean join(String communityId,CustomUserDetails customUserDetails){
        UserEntity userEntity=userRepo.findByUserName(customUserDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("no such user"));
        CommunityEntity communityEntity=communityRepo.findById(communityId).orElseThrow(()->new UsernameNotFoundException("this community does not exist"));
        if(!communityRepo.existsByCommunityIdAndMembers_UserName(communityId, customUserDetails.getUsername())){
            communityEntity.getMembers().add(userEntity);
            userEntity.getCommunities().add(communityEntity);
            communityRepo.save(communityEntity);
            userRepo.save(userEntity);
            return true;
        }else{
            return false;
        }

    }
    public List<MessageEntity> oldChats(String communityId){
        return messageRepo.findByCommunityIdOrderByMessageTimeAsc(communityId);
    }

    public List<CommunitiesResponseDTO> featuredCommunitiesService(){
        List<CommunityEntity> communityEntities=communityRepo.findAll();
        List<CommunitiesResponseDTO> communities=new ArrayList<>();
        for(CommunityEntity community:communityEntities){
            communities.add(new CommunitiesResponseDTO(community.getCommunityId(), community.getCommunityName(), community.getCommunityDescription(), community.getMembers().size()));
        }
        return communities;
    }

    public List<CommunitiesResponseDTO> findCommunities(String query) {
        List<CommunityEntity> communityEntities=communityRepo.findByCommunityNameContainingIgnoreCase(query);
        List<CommunitiesResponseDTO> communities=new ArrayList<>();
        for(CommunityEntity community:communityEntities){
            communities.add(new CommunitiesResponseDTO(community.getCommunityId(), community.getCommunityName(), community.getCommunityDescription(), community.getMembers().size()));
        }
        return communities;
    }

    public CommunityDetailsDTO getCommunityById(String communityId) {
        CommunityEntity communityEntity=communityRepo.findById(communityId).orElseThrow(()-> new UsernameNotFoundException("no such community exists"));
        CommunityDetailsDTO communityDetailsDTO=new CommunityDetailsDTO(communityEntity.getCommunityId(),communityEntity.getCommunityName(),communityEntity.getHostName(),communityEntity.getCommunityDescription(),communityEntity.getMembers().size());
        return communityDetailsDTO;
    }

    public List<CommunitiesResponseDTO> getAllCommunities() {
        List<CommunityEntity> communityEntityList=communityRepo.findAll();
        List<CommunitiesResponseDTO> communities=new ArrayList<>();
        for(CommunityEntity communityEntity:communityEntityList){
            communities.add(new CommunitiesResponseDTO(communityEntity.getCommunityId(), communityEntity.getCommunityName(), communityEntity.getCommunityDescription(), communityEntity.getMembers().size()));
        }
        return communities;
    }
}
