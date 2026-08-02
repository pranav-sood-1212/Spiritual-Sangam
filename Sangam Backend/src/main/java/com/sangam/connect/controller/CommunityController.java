package com.sangam.connect.controller;

import com.sangam.connect.DTO.CommunitiesResponseDTO;
import com.sangam.connect.DTO.CommunityDetailsDTO;
import com.sangam.connect.DTO.GeneralResponseDTO;
import com.sangam.connect.security.CustomUserDetails;
import com.sangam.connect.service.CommunityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

//    @PostMapping("/createCommunity")
//    public ResponseEntity<String> createCommunity(@RequestBody CreateCommunityRequest createCommunityRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails){
//        communityService.create(createCommunityRequest,customUserDetails);
//        return new ResponseEntity<>("community created", HttpStatus.OK);
//    }
//    @PostMapping("/join")
//    public ResponseEntity<String> joinCommunity(@RequestBody JoinCommunityRequest joinCommunityRequest,@AuthenticationPrincipal CustomUserDetails customUserDetails){
//        if(communityService.join(joinCommunityRequest,customUserDetails)){
//            return new ResponseEntity<>("you joined the community"+joinCommunityRequest.getCommunityId(),HttpStatus.OK);
//        }
//        return new ResponseEntity<>("you are already in the community",HttpStatus.FOUND);
//    }
//    @GetMapping("/getOldChats/{communityId}")
//    public ResponseEntity<List<MessageEntity>> getOldChats(@PathVariable String communityId){
//        return new ResponseEntity<>(communityService.oldChats(communityId),HttpStatus.OK);
//    }

    @GetMapping("/featured")
    public ResponseEntity<List<CommunitiesResponseDTO>> featuredCommunities(){
        return ResponseEntity.ok(communityService.featuredCommunitiesService());
    }

    @GetMapping("/search")
    public ResponseEntity<List<CommunitiesResponseDTO>> searchCommunities(@RequestParam String query){
        return ResponseEntity.ok(communityService.findCommunities(query));
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<CommunityDetailsDTO> detailsOfCommunity(@PathVariable String communityId){
        return ResponseEntity.ok(communityService.getCommunityById(communityId));
    }

    @PostMapping("/{communityId}/join")
    public ResponseEntity<GeneralResponseDTO> joinCommunity(@PathVariable String communityId,@AuthenticationPrincipal CustomUserDetails customUserDetails){
        if(communityService.join(communityId,customUserDetails)){
            return ResponseEntity.ok(new GeneralResponseDTO("you successfully joined the community"));
        }else{
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new GeneralResponseDTO("you are already a member of this community"));
        }

    }

    @GetMapping("/allCommunities")
    public ResponseEntity<List<CommunitiesResponseDTO>> allCommunities(){
        return ResponseEntity.ok(communityService.getAllCommunities());
    }
}
