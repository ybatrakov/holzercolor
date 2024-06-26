package com.yura.holzercolor.model;

import java.util.Map;

public interface Formula {
    Integer getId();
    Paint getPaint();
    Palette getPalette();
    Base getBase();
    Map<String, Double> getValues();

    void setPaint(Paint paint);
    void setPalette(Palette palette);
    void setBase(Base base);
    void setValues(Map<String, Double> values);
}
