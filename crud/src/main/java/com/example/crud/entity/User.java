package com.example.crud.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// this is a entity class and it represents a table in database
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;

    @Column(unique = true , nullable = false)
    private String username ;

    @Column(nullable = false)
    private String password;
    

     // ===== EMAIL VERIFICATION FIELDS (ADD THIS PART ONLY) =====
     
    @Column(nullable = false , unique = true)
    private String email;
    // ===== EMAIL VERIFICATION FIELDS (ADD THIS PART ONLY) =====

// account will remain false until email is verified
@Column(nullable = false)
private boolean enabled = false;

private String verificationToken;

private Long tokenExpiry;

    @OneToOne(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    UserInfo userInfo  ;

     @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Like> likes =new ArrayList<>();

     @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Comment> comments  = new ArrayList<>() ;
    boolean visible;


    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        posts.remove(post);
        post.setUser(null);
    }


}