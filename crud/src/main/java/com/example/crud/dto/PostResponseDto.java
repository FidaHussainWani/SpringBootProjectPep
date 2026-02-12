package com.example.crud.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PostResponseDto {
   private String content;
   private String mediaUrl;
   private LocalDateTime createdAt;
   private String username;

}
