package com.sangam.connect.DTO;

public record CommunityDetailsDTO(String communityId ,
                                  String communityName ,
                                  String hostName,
                                  String communityDescription ,
                                  int communityMembers
                                  ) {
}
