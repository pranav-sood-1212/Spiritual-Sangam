package com.sangam.connect.security;

import com.sangam.connect.entity.UserEntity;
import com.sangam.connect.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UserRepo userRepo;

    public UserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity=userRepo.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("no such user"));
        return new CustomUserDetails(userEntity);
    }
}
