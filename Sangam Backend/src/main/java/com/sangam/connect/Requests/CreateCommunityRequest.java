package com.sangam.connect.Requests;

import java.util.UUID;

public class CreateCommunityRequest {
    private String communityId;
    private String communityName;
    private String communityDescription;



    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityDescriptionDescription() {
        return communityDescription;
    }

    public void setCommunityDescription(String description) {
        communityDescription = description;
    }
}
