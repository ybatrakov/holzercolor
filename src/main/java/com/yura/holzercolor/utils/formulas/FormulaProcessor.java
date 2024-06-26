package com.yura.holzercolor.utils.formulas;

import com.yura.holzercolor.model.Formula;

import java.util.List;

public interface FormulaProcessor<FormulaType extends Formula> {
    void process(List<FormulaType> formulas);
}
