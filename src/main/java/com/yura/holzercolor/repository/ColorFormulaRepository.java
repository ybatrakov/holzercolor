package com.yura.holzercolor.repository;

import com.yura.holzercolor.model.ColorFormula;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ColorFormulaRepository extends CrudRepository<ColorFormula, Integer> {
    ColorFormula getByPaletteIdAndPaintId(Integer paletteId, Integer paintId);

    void deleteByPaintId(Integer paintId);
}
