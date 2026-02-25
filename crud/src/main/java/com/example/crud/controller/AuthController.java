package com.example.crud.controller;


import com.example.crud.Authentication.JwtUtil;
import com.example.crud.dto.LoginRequestDto;
import com.example.crud.dto.UserDto;
import com.example.crud.entity.Token;
import com.example.crud.entity.User;
import com.example.crud.entity.UserInfo;
import com.example.crud.entity.VerificationStatus;
import com.example.crud.repository.TokenRepository;
import com.example.crud.repository.UserRepository;
import com.example.crud.service.UserService;
import com.example.crud.entity.Role;
import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto request) {

       User user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getPassword().equals(request.getPassword())) {

            String token = jwtUtil.generateToken(
                    request.getUsername(),
                    "USER"
            );

            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

  
@PostMapping("/register")
public ResponseEntity<String>register(@RequestBody UserDto userDto){
    try{
        userService.createUser(userDto);
        return ResponseEntity.ok("User registered Successfully");
    }catch(MessagingException e){
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}


    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String token) {

      Token t  = tokenRepository.findByToken(token) ;
      User user = t.getUser() ;
       user.getUserInfo().setRole(Role.ROLE_USER);
      user.getUserInfo().setVerificationStatus(VerificationStatus.VERIFIED);
        userRepository.save(user);

      return ResponseEntity.ok("User is verified") ;

    }
    
}