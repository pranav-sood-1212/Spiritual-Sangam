package com.sangam.connect.entity;

import com.sangam.connect.enums.AccountStatus;
import jakarta.persistence.*;


import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "users")
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id" , columnDefinition = "Binary(16)", nullable = false, updatable = false)
    private UUID userId;
    @Column(name = "user_name" , columnDefinition = "VarChar(100)", nullable = false,unique = true)
    private String userName;
    @Column(name = "email" , columnDefinition = "VarChar(200)", nullable = false,unique = true)
    private String email;
    @Column(name = "password" , columnDefinition = "VarChar(100)", nullable = false)
    private String password;
    @Column(name = "phone_number_hash", columnDefinition = "CHAR(64)", nullable = false, unique = true)
    private String phoneNumberHash;
    @Column(name = "encrypted_pii", columnDefinition = "TEXT")
    private String encryptedPII;
    @Column(name = "account_status", columnDefinition = "VarChar(32)")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;


    @ManyToMany(mappedBy = "members")
    private List<CommunityEntity> communities;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getPhoneNumberHash() {
        return phoneNumberHash;
    }

    public void setPhoneNumberHash(String phoneNumberHash) {
        this.phoneNumberHash = phoneNumberHash;
    }

    public String getEncryptedPII() {
        return encryptedPII;
    }

    public void setEncryptedPII(String encryptedPII) {
        this.encryptedPII = encryptedPII;
    }



    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<CommunityEntity> getCommunities() {
        return communities;
    }

    public void setCommunities(List<CommunityEntity> communities) {
        this.communities = communities;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
