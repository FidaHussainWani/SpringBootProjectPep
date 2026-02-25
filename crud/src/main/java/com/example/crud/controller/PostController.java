package com.example.crud.controller;

import com.example.crud.Authentication.JwtUtil;
import com.example.crud.dto.PostResponseDto;
import com.example.crud.entity.Post;
import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import com.example.crud.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
   private final PostService postService;
   private final JwtUtil jwtUtil;
   private final UserRepository userRepository;
   @PostMapping(consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('USER' , 'ADMIN')")
public ResponseEntity<PostResponseDto>create(
    @RequestParam("content")String content,
    @RequestParam(value="file",required=false)MultipartFile file,
     Authentication authentication){
    
        String username= authentication.getName();

      User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        PostResponseDto response = postService.create(content, file, user);

        return ResponseEntity.ok(response);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('USER' ,'ADMIN')")
    public ResponseEntity<PostResponseDto> update(
            @PathVariable Long id,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Authentication authentication ){


        String username = authentication.getName();

        PostResponseDto response = postService.update(id, content, file, username);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN' , 'UNVERIFIED')")
    public ResponseEntity<List<PostResponseDto>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponseDto> delete(
            @PathVariable Long id,
           Authentication authentication) {
        String username = authentication.getName();

        return ResponseEntity.ok(postService.delete(id, username));
    }
}
