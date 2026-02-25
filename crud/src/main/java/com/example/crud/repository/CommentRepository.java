package com.example.crud.repository;

import com.example.crud.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findAllByUser_Id(Long userId);

    List<Comment> findByPost_IdOrderByCreatedAtDesc(Long postId);

    long countByPost_Id(Long postId);

    List<Comment> findByPost_Id(Long postId);
}