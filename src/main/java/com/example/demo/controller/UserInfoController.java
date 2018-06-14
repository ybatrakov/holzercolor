package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.validation.constraints.*;
import java.util.Optional;

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
