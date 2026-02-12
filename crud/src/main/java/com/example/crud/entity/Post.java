package com.example.crud.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private Long id;
    private String content;
    private String contentUrl;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

     @OneToMany(cascade = CascadeType.ALL)
    List<Like> likes  = new ArrayList<>() ;


   
    @PrePersist
    public void prePersist(){
        this.createdAt=LocalDateTime.now();
    }


}
