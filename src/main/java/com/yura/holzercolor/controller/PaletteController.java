package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.Palette;
import com.yura.holzercolor.repository.PaletteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaletteController {
    @Autowired
    PaletteRepository paletteRepository;

    @GetMapping("/palettes")
    public Iterable<Palette> getPalettes(String type) {
        return type != null ? paletteRepository.getByPaletteTypeNick(type) : paletteRepository.findAll();
    }
}
