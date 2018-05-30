package com.example.demo.controller;

import com.example.demo.model.PaintPrice;
import com.example.demo.model.PaintPriceFacade;
import com.example.demo.repository.PaintPriceRepository;
import com.example.demo.repository.PaintPriceFacadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PaintPriceController {
    @Autowired
    PaintPriceRepository paintPriceRepository;

    @Autowired
    PaintPriceFacadeRepository paintPriceFacadeRepository;

    @GetMapping("/paint_price")
    public Optional<PaintPrice> getPaintPrice() {
        return paintPriceRepository.findById(6);
    }

    @GetMapping("/paint_price_facade")
    public Optional<PaintPriceFacade> getPaintPriceFacade() {
        return paintPriceFacadeRepository.findById(1);
    }
}
