package com.example.MyAPP.controller;

import com.example.MyAPP.dto.AuthResponseDto;
import com.example.MyAPP.dto.UserRegistrationDto;
import com.example.MyAPP.dto.UserResponseDto;
import com.example.MyAPP.entity.User;
import com.example.MyAPP.repository.UserRepository;
import com.example.MyAPP.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {
    @Autowired
    private JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(PasswordEncoder passwordEncoder, UserRepository userRepository,AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager=authenticationManager;
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRegistrationDto userRegistrationDto){
        User user=new User();
        user.setUsername(userRegistrationDto.username());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.password()));
        user.setRoles( List.of("ROLE_USER"));
        userRepository.save(user);
        return new ResponseEntity<>(new UserResponseDto(user.getUsername(),user.getRoles()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRegistrationDto loginRequest){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(),loginRequest.password()));
            String token=jwtUtils.createToken(loginRequest.username());
            return new ResponseEntity<>(new AuthResponseDto(token),HttpStatus.OK);
        }catch(AuthenticationException e){
            return new ResponseEntity<>("invalid credentials",HttpStatus.UNAUTHORIZED);

        }

    }




}
