package com.sangam.connect.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sangam.connect.DTO.GeneralResponseDTO;
import com.sangam.connect.DTO.LoginResponseDTO;
import com.sangam.connect.Requests.UserLoginRequest;
import com.sangam.connect.Requests.UserRegisterRequest;
import com.sangam.connect.Utils.JwtUtils;
import com.sangam.connect.entity.UserEntity;
import com.sangam.connect.repository.UserRepo;
import com.sangam.connect.security.Hashing;
import org.apache.juli.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationProvider authenticationProvider;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    public UserController(AuthenticationManager authenticationManager, AuthenticationProvider authenticationProvider, UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.authenticationProvider = authenticationProvider;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRepo.existsByUserName(userRegisterRequest.getUserName())){
            return new ResponseEntity<>(new GeneralResponseDTO("username exists already"),HttpStatus.CONFLICT);
        }
        if(userRepo.existsByEmail(userRegisterRequest.getEmail())){
            return new ResponseEntity<>(new GeneralResponseDTO("email exists already"),HttpStatus.CONFLICT);
        }
        if(userRepo.existsByPhoneNumberHash(userRegisterRequest.getPhoneNumber())){
            return new ResponseEntity<>(new GeneralResponseDTO("phnNo exists already"),HttpStatus.CONFLICT);
        }
        Hashing hashing=new Hashing();
        UserEntity userEntity=new UserEntity();
        userEntity.setUserName(userRegisterRequest.getUserName());
        userEntity.setEmail(userRegisterRequest.getEmail());
        userEntity.setPhoneNumberHash(hashing.hashPhnNo(userRegisterRequest.getPhoneNumber()));
        userEntity.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        userRepo.save(userEntity);
        return new ResponseEntity<>(new GeneralResponseDTO("signed-up"),HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginRequest userLoginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequest.getUserName(),
                            userLoginRequest.getPassword()
                    )
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return new ResponseEntity<>(new LoginResponseDTO(jwtUtils.generateToken(userDetails),"login-successful" ),HttpStatus.OK);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponseDTO(null,"Invalid username or password"));
        }

    }

}
