package com.yura.holzercolor.repository;

import com.yura.holzercolor.model.ColorFormulaFacade;
import org.springframework.data.repository.CrudRepository;

public interface ColorFormulaFacadeRepository extends CrudRepository<ColorFormulaFacade, Integer> {
    ColorFormulaFacade getByPaletteIdAndPaintId(Integer paletteId, Integer paintId);
}
