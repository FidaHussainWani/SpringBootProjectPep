package com.example.crud.service;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.crud.Authentication.CustomUserDetails;
import com.example.crud.entity.UserRelationship;
import com.example.crud.service.RelationshipService;
import com.example.crud.entity.RelationshipStatus;
import com.example.crud.repository.UserRelationshipRepository;
import com.example.crud.Authentication.CustomUserDetails;
import java.util.Optional;
import java.util.*;

@Service
public class RelationshipService {

    private final UserRelationshipRepository repo;

    public RelationshipService(UserRelationshipRepository repo) {
        this.repo = repo;
    }

    private Long getLoggedInUserId() {
        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) auth.getPrincipal();

        return userDetails.getId();
    }

    public String followUser(Long targetUserId) {

        Long followerId = getLoggedInUserId();

        if (followerId.equals(targetUserId)) {
            return "You cannot follow yourself";
        }

        Optional<UserRelationship> existing =
                repo.findByFollowerIdAndFollowingId(followerId, targetUserId);

        if (existing.isPresent()) {
            if (existing.get().getStatus() == RelationshipStatus.BLOCK) {
                return "You blocked this user";
            }
            return "Already following";
        }

        UserRelationship rel = new UserRelationship();
        rel.setFollowerId(followerId);
        rel.setFollowingId(targetUserId);
        rel.setStatus(RelationshipStatus.FOLLOW);

        repo.save(rel);
        return "User followed";
    }

    public String unfollowUser(Long targetUserId) {

        Long followerId = getLoggedInUserId();

        Optional<UserRelationship> rel =
                repo.findByFollowerIdAndFollowingId(followerId, targetUserId);

        if (rel.isPresent() &&
                rel.get().getStatus() == RelationshipStatus.FOLLOW) {
            repo.delete(rel.get());
            return "Unfollowed";
        }

        return "Not following";
    }

    public String blockUser(Long targetUserId) {

        Long blockerId = getLoggedInUserId();

        Optional<UserRelationship> existing =
                repo.findByFollowerIdAndFollowingId(blockerId, targetUserId);

        UserRelationship rel = existing.orElse(new UserRelationship());

        rel.setFollowerId(blockerId);
        rel.setFollowingId(targetUserId);
        rel.setStatus(RelationshipStatus.BLOCK);

        repo.save(rel);

        // remove reverse follow if exists
        repo.findByFollowerIdAndFollowingId(targetUserId, blockerId)
                .ifPresent(repo::delete);

        return "User blocked";
    }

    public String unblockUser(Long targetUserId) {

        Long blockerId = getLoggedInUserId();

        Optional<UserRelationship> rel =
                repo.findByFollowerIdAndFollowingId(blockerId, targetUserId);

        if (rel.isPresent() &&
                rel.get().getStatus() == RelationshipStatus.BLOCK) {
            repo.delete(rel.get());
            return "User unblocked";
        }

        return "User not blocked";
    }
}
