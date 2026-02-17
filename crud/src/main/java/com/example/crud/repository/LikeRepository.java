package com.example.crud.repository;

import com.example.crud.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

        public List<Like> findAllByUserId(long id) ;

        Optional<Like> findByUserIdAndPostId(Long userId, Long postId);

        long countByPostId(Long postId);

        boolean existsByUserIdAndPostId(Long userId, Long postId);

        void deleteByUserIdAndPostId(Long userId, Long postId);
}