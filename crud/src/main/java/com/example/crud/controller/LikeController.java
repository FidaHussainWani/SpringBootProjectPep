package com.example.crud.controller;


import com.example.crud.Authentication.JwtUtil;
import com.example.crud.service.LikeService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;
    private final JwtUtil jwtUtil;


//    Post http://localhost:8080/like/toggle?postId=1
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleLike(
            @RequestParam Long postId,
            Authentication authentication) {
            String username = authentication.getName();    
        String response = likeService.toggleLike(username, postId);
        return ResponseEntity.ok(response);
    }

//    GET http://localhost:8080/like/count/1
    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {

        long count = likeService.getLikeCount(postId);
        return ResponseEntity.ok(count);
    }


    // GET http://localhost:8080/like/is-liked?postId=1
    @GetMapping("/is-liked")
    public ResponseEntity<Boolean> isLiked(
            @RequestParam Long postId,
            Authentication authentication) {
            String username=authentication.getName();
        boolean liked = likeService.isLiked(username, postId);
        return ResponseEntity.ok(liked);
    }
}