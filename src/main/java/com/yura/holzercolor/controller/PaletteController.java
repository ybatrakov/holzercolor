package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.Palette;
import com.yura.holzercolor.repository.PaletteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaletteController {
    private final PaletteRepository paletteRepository;

    public PaletteController(PaletteRepository paletteRepository) {
        this.paletteRepository = paletteRepository;
    }

    @GetMapping("/palettes")
    public Iterable<Palette> getPalettes(int typeId) {
        return paletteRepository.findByPaletteTypeId(typeId);
    }
}
