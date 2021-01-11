package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.PaintPrice;
import com.yura.holzercolor.model.PaintPriceFacade;
import com.yura.holzercolor.model.UserProfile;
import com.yura.holzercolor.repository.PaintPriceRepository;
import com.yura.holzercolor.repository.PaintPriceFacadeRepository;
import com.yura.holzercolor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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

    @GetMapping("/paint_prices")
    public Iterable<PaintPrice> getPaintPrices() {
        return paintPriceRepository.findAll();
    }

    @GetMapping("/paint_prices_facade")
    public Iterable<PaintPriceFacade> getPaintPricesFacade() {
        return paintPriceFacadeRepository.findAll();
    }
}
