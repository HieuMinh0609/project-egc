package com.example.file.processor;


import com.example.file.dto.UserModel;
import com.example.file.entity.Users;
import com.example.file.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class UserProcessor {

    UserService userService;

    public UserProcessor(UserService userService) {
        this.userService = userService;
    }


    public UserModel get(Long userId) {
        return convertTo(userService.get(userId).get());
    }

    private UserModel convertTo(Users user) {
        UserModel userModel = UserModel.builder()
                .name(user.getName())
                .phone(user.getPhone())
                .id(user.getId())
                .createDate(user.getCreateDate())
                .build();

        return userModel;
    }

    public Page<UserModel> get(String keyword, Integer page, Integer size) {
        return userService.get(keyword, page, size).map(item -> map(item));
    }

    protected UserModel map(Users user) {
        UserModel userModel = UserModel.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .name(user.getName())
                .createDate(user.getCreateDate())
                .build();

        return userModel;
    }
}
