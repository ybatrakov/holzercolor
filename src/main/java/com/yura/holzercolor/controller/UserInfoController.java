package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.User;
import com.yura.holzercolor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api")
public class UserInfoController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/user_info")
    public User getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName());
    }
}
