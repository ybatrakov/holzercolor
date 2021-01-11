package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.User;
import com.yura.holzercolor.repository.UserProfileRepository;
import com.yura.holzercolor.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api")
public class UserInfoController {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/user_info")
    public User getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName());
    }

    @GetMapping("/users")
    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User newUser(@RequestBody User user) {
        logger.info("Attempt to save user {}", user.getEmail());

        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new ObjectAlreadyExistsException("User already exists");
        }

        if(user.getProfile().getFee() == null) {
            user.getProfile().setFee(0);
        }
        userProfileRepository.save(user.getProfile());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
