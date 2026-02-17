package com.example.crud.controller;

import com.example.crud.entity.Comment;
import com.example.crud.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Add comment to a post
     * Example:
     * POST /api/comments?userId=1&postId=10
     * Body: "Nice post!"
     */
    @PostMapping
    public ResponseEntity<Comment> addComment(
            @RequestParam Long userId,
            @RequestParam Long postId,
            @RequestBody String content) {

        Comment comment = commentService.addComment(userId, postId, content);
        return ResponseEntity.ok(comment);
    }

    /**
     * Update comment
     * Example:
     * PUT /api/comments/5?userId=1
     * Body: "Updated comment"
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @RequestParam Long userId,
            @RequestBody String content) {

        Comment updated = commentService.updateComment(commentId, userId, content);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete comment
     * Example:
     * DELETE /api/comments/5?userId=1&isAdmin=false
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @RequestParam Long userId,
            @RequestParam(defaultValue = "false") boolean isAdmin) {

        commentService.deleteComment(commentId, userId, isAdmin);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    /**
     * Get all comments of a post
     * Example:
     * GET /api/comments/post/10
     */
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(
            @PathVariable Long postId) {

        List<Comment> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Count comments of a post
     * Example:
     * GET /api/comments/count/10
     */
    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> countComments(
            @PathVariable Long postId) {

        long count = commentService.countComments(postId);
        return ResponseEntity.ok(count);
    }
}
