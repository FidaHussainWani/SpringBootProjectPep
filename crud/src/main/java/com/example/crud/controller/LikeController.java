package com.example.crud.controller;


import org.springframework.web.bind.annotation.RestController;


import com.example.crud.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * Toggle like/unlike for a post by a user
     * Example:
     * POST /api/likes/toggle?userId=1&postId=10
     */
    @PostMapping("/toggle")
    public ResponseEntity<String> toggleLike(
            @RequestParam Long userId,
            @RequestParam Long postId) {

        String response = likeService.toggleLike(userId, postId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get total like count of a post
     * Example:
     * GET /api/likes/count/10
     */
    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) {

        long count = likeService.getLikeCount(postId);
        return ResponseEntity.ok(count);
    }

    /**
     * Check if a user liked a post
     * Example:
     * GET /api/likes/is-liked?userId=1&postId=10
     */
    @GetMapping("/is-liked")
    public ResponseEntity<Boolean> isLiked(
            @RequestParam Long userId,
            @RequestParam Long postId) {

        boolean liked = likeService.isLiked(userId, postId);
        return ResponseEntity.ok(liked);
    }
}