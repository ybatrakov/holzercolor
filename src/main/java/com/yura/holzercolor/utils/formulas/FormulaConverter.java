package com.yura.holzercolor.utils.formulas;

import com.yura.holzercolor.model.Formula;
import com.yura.holzercolor.model.Paint;

import java.util.List;
import java.util.stream.Stream;

public interface FormulaConverter<FormulaType extends Formula> {
    List<FormulaType> convert(Paint paint, double volume, Stream<String> lines);
}
