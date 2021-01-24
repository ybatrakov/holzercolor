package com.yura.holzercolor.model;

import java.util.Map;

public interface Formula {
    enum Type {
        REGULAR, FACADE
    };

    Integer getId();
    Paint getPaint();
    Palette getPalette();
    Base getBase();
    Map<String, Double> getValues();

    void setPaint(Paint paint);
    void setPalette(Palette palette);
    void setBase(Base base);
    void setValue(String field, Double value);
}
