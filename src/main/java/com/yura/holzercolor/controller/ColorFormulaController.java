package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.ColorFormula;
import com.yura.holzercolor.model.ColorFormulaFacade;
import com.yura.holzercolor.repository.ColorFormulaRepository;
import com.yura.holzercolor.repository.ColorFormulaFacadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
