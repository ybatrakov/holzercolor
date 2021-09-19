package com.yura.holzercolor.utils.formulas;

import java.util.List;

public interface FormulaFileListener {
    void onFormulaInfoResolved(Formula.Type formulaType);
    void onFormulas(List<Formula> batch);
}
