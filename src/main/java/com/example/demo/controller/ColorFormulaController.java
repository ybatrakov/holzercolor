package com.example.demo.controller;

import com.example.demo.model.ColorFormula;
import com.example.demo.model.ColorFormulaFacade;
import com.example.demo.repository.ColorFormulaRepository;
import com.example.demo.repository.ColorFormulaFacadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;

@RestController
@RequestMapping("/api")
public class ColorFormulaController {
    @Autowired
    ColorFormulaRepository colorFormulaRepository;

    @Autowired
    ColorFormulaFacadeRepository colorFormulaFacadeRepository;

    @GetMapping("/color_formulas")
    public ColorFormula getColorFormula(Integer paletteId, Integer paintId) {
        return colorFormulaRepository.getByPaletteIdAndPaintId(paletteId, paintId);
    }

    @GetMapping("/color_formulas_facade")
    public ColorFormulaFacade getColorFormulaFacade(Integer paletteId, Integer paintId) {
        return colorFormulaFacadeRepository.getByPaletteIdAndPaintId(paletteId, paintId);
    }
}
