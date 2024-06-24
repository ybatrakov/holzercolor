package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.PaletteType;
import com.yura.holzercolor.repository.PaletteTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaletteTypeController {
    private final PaletteTypeRepository paletteTypeRepository;

    public PaletteTypeController(PaletteTypeRepository paletteTypeRepository) {
        this.paletteTypeRepository = paletteTypeRepository;
    }

    @GetMapping("/palette_types")
    public Iterable<PaletteType> getPaletteTypes(String formulaType) {
        return paletteTypeRepository.findByFormulaTypeName(formulaType);
    }
}
