package com.example.crud.service;


import com.example.crud.dto.UserDto;
import com.example.crud.dto.UserResponseDto;
import com.example.crud.entity.UserInfo;
import com.example.crud.repository.UserInfoRepository;
import com.example.crud.repository.UserRepository;
import com.example.crud.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;

    private User toEntity(UserDto userDto){
        User user = new User();
        UserInfo userInfo = new UserInfo();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        userInfo.setName(userDto.getName());
        userInfo.setPhone(userDto.getPhone());
        userInfo.setProfilePic(userDto.getProfilePic());
        userInfo.setUser(user);
        user.setUserInfo(userInfo);

        return user;
    }

    private UserDto toDto(User user){
        UserDto userDto = new UserDto();
        UserInfo userInfo = user.getUserInfo();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(userInfo.getPhone());
        userDto.setProfilePic(userInfo.getProfilePic());
        userDto.setName(userInfo.getName());

        return userDto;

    }

    private UserResponseDto toResponseDto(User user){
        UserResponseDto userDto = new UserResponseDto();
        UserInfo userInfo = user.getUserInfo();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setName(userInfo.getName());

        return userDto;
    }
    public UserResponseDto createUser(UserDto userDto) {


        User user   = toEntity(userDto) ;
        userRepository.save(user);
        userInfoRepository.save(user.getUserInfo());

        return toResponseDto(user);

    }

    public List<UserResponseDto> getUsers() {

        List<User> users  = userRepository.findAll();
        List<UserResponseDto> response = new ArrayList<>();
        for (User user : users) {
            response.add(toResponseDto(user));
        }
        return response;
    }

    public UserResponseDto getuser(long id){
        User  user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return toResponseDto(user);

    }


    public UserResponseDto updateUserPartial(UserDto userDto) {

        User user = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserInfo userInfo = user.getUserInfo();

        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }

        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }

        if (userDto.getName() != null) {
            userInfo.setName(userDto.getName());
        }

        if (userDto.getPhone() != null) {
            userInfo.setPhone(userDto.getPhone());
        }

        if (userDto.getProfilePic() != null) {
            userInfo.setProfilePic(userDto.getProfilePic());
        }

        userRepository.save(user);
        userInfoRepository.save(userInfo);

        return toResponseDto(user);
    }



    public UserResponseDto deleteUser(long id) {
        User  user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
        return toResponseDto(user);
    }




}