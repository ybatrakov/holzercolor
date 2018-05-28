package com.example.demo.controller;

import com.example.demo.model.PaintPrice;
import com.example.demo.repository.PaintPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PaintPriceController {
    @Autowired
    PaintPriceRepository paintPriceRepository;

    @GetMapping("/paint_price")
    public Optional<PaintPrice> getPaintPrice() {
        return paintPriceRepository.findById(6);
    }
}
