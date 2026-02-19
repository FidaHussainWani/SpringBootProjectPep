package com.example.crud.Authentication;


import com.example.crud.entity.User;
import com.example.crud.repository.UserRepository;
import com.example.crud.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserService implements UserDetailsService{

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username);

        return new CustomUserDetails(user) ;

    }
}
