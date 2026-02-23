package com.example.crud.service;

import com.example.crud.dto.PostResponseDto;
import com.example.crud.entity.Post;
import com.example.crud.entity.User;
import com.example.crud.repository.PostRepository;
import com.example.crud.repository.UserRepository;
import com.example.crud.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostService {
     @Autowired
    private PostRepository postRepository;
  @Autowired
  private FileUploadService fileUploadService;
  @Autowired
  private UserRepository userRepository;


    private PostResponseDto toDto(Post post){
        String  username = post.getUser().getUsername();
        PostResponseDto postResponseDto = new PostResponseDto();
         postResponseDto.setId(post.getId()); 
        postResponseDto.setUsername(username);
        postResponseDto.setContent(post.getContent());
        postResponseDto.setCreatedAt(post.getCreatedAt());
        postResponseDto.setMediaUrl(post.getContentUrl());

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
        Post post = postRepository.findById(id)
    .orElseThrow(() -> new RuntimeException("Post not found"));
        return toDto(post);
    }

    public PostResponseDto delete(long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new RuntimeException("post not found"));
        postRepository.delete(post);

        return toDto(post);

    }

    public PostResponseDto create(String content,MultipartFile file, User user){
        Post post=new Post();
        post.setContent(content);
        post.setUser(user);
       if (file != null && !file.isEmpty()){ String url =fileUploadService.uploadFile(file);
        post.setContentUrl(url);
       }

       Post saved= postRepository.save(post);
        return toDto(saved) ;
    }

    public PostResponseDto update(Post post){

        Post oldPost = postRepository.findById(post.getId()).orElseThrow(()-> new RuntimeException("post not found"));

        if(post.getContent() !=null){
            oldPost.setContent(post.getContent());
        }
        if(post.getContentUrl() !=null){
            oldPost.setContentUrl(post.getContentUrl());
        }


       Post saved = postRepository.save(oldPost);


        return toDto(saved) ;
    }
   
}
