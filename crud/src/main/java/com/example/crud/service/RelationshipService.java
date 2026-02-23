package com.example.crud.service;

import com.example.crud.entity.User;
import com.example.crud.entity.UserRelationship;
import com.example.crud.entity.RelationshipStatus;
import com.example.crud.repository.UserRelationshipRepository;
import com.example.crud.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RelationshipService {

    private final UserRelationshipRepository repo;
    private final UserRepository userRepository;

    public RelationshipService(UserRelationshipRepository repo,
                               UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    // ⭐ PUT METHOD HERE
    private Long getLoggedInUserId() {

        Authentication auth = SecurityContextHolder
                .getContext().getAuthentication();

        String username = auth.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getId();
    }

    // ⭐ USE IT INSIDE FOLLOW METHOD
    public String followUser(Long targetUserId) {

        Long followerId = getLoggedInUserId();

        if (followerId.equals(targetUserId)) {
            return "You cannot follow yourself";
        }

        Optional<UserRelationship> existing =
                repo.findByFollowerIdAndFollowingId(followerId, targetUserId);

        if (existing.isPresent()) {
            return "Already following or blocked";
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

    return "You Unfollowed are not following this user now..";
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

    return "User was not blocked";
}
}