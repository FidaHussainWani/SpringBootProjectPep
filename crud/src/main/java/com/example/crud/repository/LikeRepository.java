package com.example.crud.repository;

import com.example.crud.entity.Like;
import com.example.crud.entity.Post;
import com.example.crud.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

        public List<Like> findAllByUserId(long id) ;

        Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

        long countByPostId(Long postId);
        Optional<Like> findByUserAndPost(User user, Post post);
        boolean existsByUserAndPost(User user, Post post);

        // void deleteByUserIdAndPost(Long userId, Long postId);
}