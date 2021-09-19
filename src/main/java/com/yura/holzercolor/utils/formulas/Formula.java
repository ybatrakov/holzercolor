package com.yura.holzercolor.utils.formulas;

import java.util.HashMap;

public class Formula {
    public enum Type {
        REGULAR, FACADE
    }

    public int fileLine;
    public String palette;
    public String base;
    public HashMap<String, Double> componentPrices;
}
