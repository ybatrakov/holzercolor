package com.example.demo.controller;

import com.example.demo.model.Palette;
import com.example.demo.repository.PaletteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;

@RestController
@RequestMapping("/api")
public class PaletteController {
    @Autowired
    PaletteRepository paletteRepository;

    @GetMapping("/palettes")
    public Iterable<Palette> getPalettes(String type) {
        return type != null ? paletteRepository.getByTypeNick(type) : paletteRepository.findAll();
    }
}
