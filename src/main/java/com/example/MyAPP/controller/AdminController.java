package com.example.MyAPP.controller;

import com.example.MyAPP.entity.User;
import com.example.MyAPP.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<?> deleteuser(@PathVariable String userId){
        userRepository.deleteById(userId);
        return new ResponseEntity<>("user deleted", HttpStatus.OK);
    }

    // make update and add user and create admin

}
