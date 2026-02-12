package com.example.crud.service;

import com.example.crud.dto.PostResponseDto;
import com.example.crud.entity.Post;
import com.example.crud.repository.PostRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PostService {
     @Autowired
    private PostRepository postRepository;



    private PostResponseDto toDto(Post post){
        String  username = post.getUser().getUsername();
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setUsername(username);
        postResponseDto.setContent(post.getContent());
        postResponseDto.setCreatedAt(post.getCreatedAt());
        postResponseDto.setMediaUrl(post.getMediaUrl());

        return postResponseDto;

    }

    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> res = posts.stream().map(this::toDto).toList();
        return res;

    }


    public List<PostResponseDto> findAllByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByUserId(userId);
        return posts.stream().map(this::toDto).toList();
    }

    public PostResponseDto findById(Long id) {
        Post post =  postRepository.findById(id).get();
        return toDto(post);
    }

    public PostResponseDto delete(long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new RuntimeException("post not found"));
        postRepository.delete(post);

        return toDto(post);

    }

    public PostResponseDto create(Post post){
        postRepository.save(post);
        return toDto(post) ;
    }

    public PostResponseDto update(Post post){

        Post oldPost = postRepository.findById(post.getId()).orElseThrow(()-> new RuntimeException("post not found"));

        if(post.getContent() !=null){
            oldPost.setContent(post.getContent());
        }
        if(post.getMediaUrl() !=null){
            oldPost.setMediaUrl(post.getMediaUrl());
        }


        postRepository.save(post);

        return toDto(post) ;
    }
   
}
