package com.example.demo.controller;

import com.example.demo.model.PaletteType;
import com.example.demo.repository.PaletteTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;

@RestController
@RequestMapping("/api")
public class PaletteTypeController {
    @Autowired
    PaletteTypeRepository paletteTypeRepository;

    @GetMapping("/palette_types")
    public Iterable<PaletteType> getPaletteTypes() {
        return paletteTypeRepository.findAll();
    }
}
