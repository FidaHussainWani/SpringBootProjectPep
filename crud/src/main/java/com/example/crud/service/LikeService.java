package com.example.crud.service;


import com.example.crud.entity.Like;
import com.example.crud.entity.Post;
import com.example.crud.entity.User;
import com.example.crud.repository.LikeRepository;
import com.example.crud.repository.PostRepository;
import com.example.crud.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PostRepository postRepository;

    @Transactional
    public String toggleLike(Long userId, Long postId) {

        Optional<Like> existing =
                likeRepository.findByUserIdAndPostId(userId, postId);

        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            return "Post unliked";
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepository.save(like);

        return "Post liked";
    }

    public long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    public boolean isLiked(Long userId, Long postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
}