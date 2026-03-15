package com.example.MyAPP.service;

import com.example.MyAPP.entity.User;
import com.example.MyAPP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not Present"));
        String[] roles=(user.getRoles() == null || user.getRoles().isEmpty())
                ? new String[]{"ROlE_USER"}  // Default role
                : user.getRoles().toArray(new String[0]);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(roles)
                    .build();
    }


}

