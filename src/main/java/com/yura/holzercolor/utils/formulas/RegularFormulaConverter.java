package com.yura.holzercolor.utils.formulas;

import com.yura.holzercolor.model.Base;
import com.yura.holzercolor.model.ColorFormula;
import com.yura.holzercolor.model.Paint;
import com.yura.holzercolor.model.Palette;
import com.yura.holzercolor.repository.BaseRepository;
import com.yura.holzercolor.repository.PaletteRepository;

import java.util.HashMap;
import java.util.Map;

public class RegularFormulaConverter extends AbstractFormulaConverter<ColorFormula> {
    public RegularFormulaConverter(PaletteRepository paletteRepository, BaseRepository baseRepository) {
        super(paletteRepository, baseRepository);
    }

    @Override
    protected ColorFormula createFormula(Paint paint, Palette palette, Base base, Map<String, Double> params) {
        ColorFormula ret = new ColorFormula();
        ret.setPaint(paint);
        ret.setPalette(palette);
        ret.setBase(base);
        ret.setValues(params);
        return ret;
    }
}
