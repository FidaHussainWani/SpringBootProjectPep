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
    public Comment addComment(Long userId, Long postId, String content) {

        User user = userRepository.findById(userId)
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
    public Comment updateComment(Long commentId, Long userId, String content) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
          if (comment.getUser().getId()!=userId) {
        throw new RuntimeException("You can update only your comment");
    }
        comment.setComment(content);
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId, boolean isAdmin) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

          if (!isAdmin && comment.getUser().getId() != userId)
 {
        throw new RuntimeException("Not authorized to delete this comment");
    }


        commentRepository.delete(comment);
    }

    public List<Comment> getCommentsByPost(Long postId) {

        return commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
    }

    public long countComments(Long postId) {
        return commentRepository.countByPostId(postId);
    }
}