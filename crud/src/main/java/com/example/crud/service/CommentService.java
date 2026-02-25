package com.example.crud.service;


import com.example.crud.entity.Comment;
import com.example.crud.entity.Post;
import com.example.crud.entity.User;
import com.example.crud.repository.CommentRepository;
import com.example.crud.repository.PostRepository;
import com.example.crud.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    @Transactional
    public Comment addComment(String username, Long postId, String content) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setComment(content);
        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(Long commentId, String username, String content) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
         if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You are not allowed to update this comment");
        }
        comment.setComment(content);
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, String username) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

          boolean isCommentOwner =
                comment.getUser().getUsername().equals(username);

        boolean isPostOwner =
                comment.getPost().getUser().getUsername().equals(username);

        if (!isCommentOwner && !isPostOwner) {
            throw new RuntimeException("You are not allowed to delete this comment");
        }


        commentRepository.delete(comment);
    }

    public List<Comment> getCommentsByPost(Long postId) {

        return commentRepository.findByPostId(postId);
    }

    public long countComments(Long postId) {
        return commentRepository.countByPostId(postId);
    }
}