package com.example.demo.controller;

import com.example.demo.model.PaintPrice;
import com.example.demo.model.PaintPriceFacade;
import com.example.demo.model.UserProfile;
import com.example.demo.repository.PaintPriceRepository;
import com.example.demo.repository.PaintPriceFacadeRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.validation.constraints.*;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PaintPriceController {
    @Autowired
    PaintPriceRepository paintPriceRepository;

    @Autowired
    PaintPriceFacadeRepository paintPriceFacadeRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/paint_price")
    public PaintPrice getPaintPrice() {
        return getUserProfile().getPaintPrice();
    }

    @GetMapping("/paint_price_facade")
    public PaintPriceFacade getPaintPriceFacade() {
        return getUserProfile().getPaintPriceFacade();
    }

    private UserProfile getUserProfile() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName()).getProfile();
    }
}
