package com.example.crud.controller;
import com.example.crud.service.RelationshipService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userFollow")
public class RelationshipController {

    private final RelationshipService service;

    public RelationshipController(RelationshipService service) {
        this.service = service;
    }

    @PostMapping("/follow/{userId}")
    public String follow(@PathVariable Long userId) {
        return service.followUser(userId);
    }

    @DeleteMapping("/unfollow/{userId}")
    public String unfollow(@PathVariable Long userId) {
        return service.unfollowUser(userId);
    }

    @PostMapping("/block/{userId}")
    public String block(@PathVariable Long userId) {
        return service.blockUser(userId);
    }

    @DeleteMapping("/unblock/{userId}")
    public String unblock(@PathVariable Long userId) {
        return service.unblockUser(userId);
    }
}
