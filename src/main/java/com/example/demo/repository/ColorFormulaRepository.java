package com.example.demo.repository;

import com.example.demo.model.ColorFormula;
import org.springframework.data.repository.CrudRepository;

public interface ColorFormulaRepository extends CrudRepository<ColorFormula, Integer> {
    ColorFormula getByPaletteIdAndPaintId(Integer paletteId, Integer paintId);
}
