package com.example.demo.repository;

import com.example.demo.model.ColorFormulaFacade;
import org.springframework.data.repository.CrudRepository;

public interface ColorFormulaFacadeRepository extends CrudRepository<ColorFormulaFacade, Integer> {
    ColorFormulaFacade getByPaletteIdAndPaintId(Integer paletteId, Integer paintId);
}
