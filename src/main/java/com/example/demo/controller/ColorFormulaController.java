package com.example.demo.controller;

import com.example.demo.model.ColorFormula;
import com.example.demo.repository.ColorFormulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.constraints.*;

@RestController
@RequestMapping("/api")
public class ColorFormulaController {
    @Autowired
    ColorFormulaRepository colorFormulaRepository;

    @GetMapping("/color_formulas")
    public ColorFormula getColorFormula(Integer paletteId, Integer paintId) {
        return colorFormulaRepository.getByPaletteIdAndPaintId(paletteId, paintId);
    }
}
