package com.example.crud.controller;

import com.example.crud.Authentication.JwtUtil;
import com.example.crud.entity.Comment;
import com.example.crud.service.CommentService;

import lombok.RequiredArgsConstructor;


import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER' , 'ADMIN')")

public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;


    /**
     * Add comment to a post
     * Example:
     * POST /comments?postId=10
     * Body: "Nice post!"
     */
    @PostMapping
    public ResponseEntity<Comment> addComment(
            @RequestParam Long postId,
            @RequestBody String content,
            Authentication authentication) {
                String username=authentication.getName();
        Comment comment = commentService.addComment(username, postId, content);
        return ResponseEntity.ok(comment);
    }

    /**
     * Update comment
     * Example:
     * PUT /comments/5?userId=1
     * Body: "Updated comment"
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @RequestBody String content,
        @RequestHeader("Authorization")String token) {
            token=token.substring(7);
                    if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.badRequest().build();
        }

        String username = jwtUtil.extractUsername(token);

        Comment updated = commentService.updateComment(commentId, username, content);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete comment
     * Example:
     * DELETE /comments/5?userId=1&isAdmin=false
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            Authentication authentication) {
        String username = authentication.getName();
        commentService.deleteComment(commentId, username);
        return ResponseEntity.ok("Comment deleted successfully");
    }


    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(
            @PathVariable Long postId) {

        List<Comment> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }


    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> countComments(
            @PathVariable Long postId) {
                
        long count = commentService.countComments(postId);
        return ResponseEntity.ok(count);
    }
}
