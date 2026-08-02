package com.sangam.connect.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "communities")
public class CommunityEntity {
    @Id
    @Column(name = "community_id" , columnDefinition ="VarChar(100)" , updatable = false)
    private String communityId;
    @Column(name = "community_name" , columnDefinition ="VarChar(100)" , nullable = false , unique = true)
    private String communityName;
    @Column(name = "host_name" , nullable = false)
    private String hostName;
    @Column(name = "community_description" , columnDefinition ="VarChar(1000)" , nullable = false)
    private String communityDescription;
    @ManyToMany
    @JoinTable(name = "community_members" , joinColumns = @JoinColumn(name = "community_id") ,inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> members;
    public CommunityEntity(){}

    public CommunityEntity(String communityId, String communityName,String hostName, String communityDescription) {
        this.communityId = communityId;
        this.communityName = communityName;
        this.hostName = hostName;
        this.communityDescription = communityDescription;
    }

    public List<UserEntity> getMembers() {
        return members;
    }

    public void setMembers(List<UserEntity> members) {
        this.members = members;
    }

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



    public String getCommunityDescription() {
        return communityDescription;
    }

    public void setCommunityDescription(String communityDescription) {
        this.communityDescription = communityDescription;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

}
