package com.example.crud.repository;
import com.example.crud.entity.UserRelationship;
import com.example.crud.entity.RelationshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRelationshipRepository 
        extends JpaRepository<UserRelationship, Long> {

    Optional<UserRelationship> 
    findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    List<UserRelationship> 
    findByFollowingIdAndStatus(Long followingId, RelationshipStatus status);

    List<UserRelationship> 
    findByFollowerIdAndStatus(Long followerId, RelationshipStatus status);
    
}