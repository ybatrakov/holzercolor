package com.yura.holzercolor.repository;

import com.yura.holzercolor.model.ColorFormula;
import org.springframework.data.repository.CrudRepository;

public interface ColorFormulaRepository extends CrudRepository<ColorFormula, Integer> {
    ColorFormula getByPaletteIdAndPaintId(Integer paletteId, Integer paintId);
}
