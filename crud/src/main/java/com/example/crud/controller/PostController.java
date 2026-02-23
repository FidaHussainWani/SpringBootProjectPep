package com.example.crud.controller;

import com.example.crud.Authentication.JwtUtil;
import com.example.crud.dto.PostResponseDto;
import com.example.crud.entity.Post;
import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import com.example.crud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
   private final PostService postService;
   private final JwtUtil jwtUtil;
    private final UserRepository userRepository; 
   @PostMapping(consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<PostResponseDto>create(@RequestParam("content")String content,@RequestParam(value="file",required=false)MultipartFile file, @RequestHeader("Authorization")String token){
    token =token.substring(7);
     if(jwtUtil.validateToken(token)){
                String username = jwtUtil.extractUsername(token);
                  User user = userRepository.findByUsername(username);
                 PostResponseDto  response =  postService.create(content, file, user);
                return ResponseEntity.ok().body(response);
            }
    
            return ResponseEntity.badRequest().build();
}


}
