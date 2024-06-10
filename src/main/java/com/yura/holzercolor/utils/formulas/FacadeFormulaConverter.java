package com.yura.holzercolor.utils.formulas;

import com.yura.holzercolor.model.*;
import com.yura.holzercolor.repository.BaseRepository;
import com.yura.holzercolor.repository.PaletteRepository;

import java.util.Map;

public class FacadeFormulaConverter extends AbstractFormulaConverter<ColorFormulaFacade> {
    public FacadeFormulaConverter(PaletteRepository paletteRepository, BaseRepository baseRepository) {
        super(paletteRepository, baseRepository);
    }

    @Override
    protected ColorFormulaFacade createFormula(Paint paint, Palette palette, Base base, Map<String, Double> params) {
        ColorFormulaFacade ret = new ColorFormulaFacade();
        ret.setPaint(paint);
        ret.setPalette(palette);
        ret.setBase(base);
        ret.setValues(params);
        return ret;
    }
}
