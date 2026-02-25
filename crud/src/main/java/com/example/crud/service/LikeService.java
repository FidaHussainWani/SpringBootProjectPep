package com.example.crud.service;


import com.example.crud.entity.Like;
import com.example.crud.entity.Post;
import com.example.crud.entity.User;
import com.example.crud.repository.LikeRepository;
import com.example.crud.repository.PostRepository;
import com.example.crud.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

  
    private final LikeRepository likeRepository;
  
    private final UserRepository userRepository;
    private  final PostRepository postRepository;

    public String toggleLike(String username, Long postId) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found")); 
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Optional<Like> existingLike =
                likeRepository.findByUserAndPost(user, post);

        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            return "Post unliked";
        }else{
             Like like = new Like();
            like.setUser(user);
            like.setPost(post);
            likeRepository.save(like);
            return "Liked";
        }
    }

    public long getLikeCount(Long postId) {
        return likeRepository.countByPostId(postId);
    }

    public boolean isLiked(String username, Long postId) {
           User user = userRepository.findByUsername(username);
           Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        
                return likeRepository.existsByUserAndPost(user, post);
    }
}