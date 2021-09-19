package com.yura.holzercolor.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "color_formulas")
@JsonSerialize(using = ColorFormulaSerializer.class)
public class ColorFormula implements Formula, Serializable {
    @Id
    @SequenceGenerator(name="color_formulas_seq", sequenceName = "color_formulas_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "color_formulas_seq")
    private Integer id;

    @ManyToOne
    private Paint paint;

    @ManyToOne
    private Palette palette;

    @ManyToOne
    private Base base;

    public static final Set<String> FIELDS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("ft", "hs", "ks", "ls", "lt", "ms", "mt", "pt", "rs", "rt", "st", "tt", "us", "vt", "xt", "zt")));

    private double ft;
    private double hs;
    private double ks;
    private double ls;
    private double lt;
    private double ms;
    private double mt;
    private double pt;
    private double rs;
    private double rt;
    private double st;
    private double tt;
    private double us;
    private double vt;
    private double xt;
    private double zt;

    @Override
    public Map<String, Double> getValues() {
        HashMap<String, Double> ret = new HashMap<>();
        ret.put("ft", ft);
        ret.put("hs", hs);
        ret.put("ks", ks);
        ret.put("ls", ls);
        ret.put("lt", lt);
        ret.put("ms", ms);
        ret.put("mt", mt);
        ret.put("pt", pt);
        ret.put("rs", rs);
        ret.put("rt", rt);
        ret.put("st", st);
        ret.put("tt", tt);
        ret.put("us", us);
        ret.put("vt", vt);
        ret.put("xt", xt);
        ret.put("zt", zt);

        return ret;
    }

    @Override
    public final Type getType() {
        return Type.REGULAR;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Base getBase() {
        return base;
    }

    @Override
    public void setBase(Base base) {
        this.base = base;
    }

    @Override
    public Paint getPaint() {
        return paint;
    }

    @Override
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    public Palette getPalette() {
        return palette;
    }

    @Override
    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    @Override
    public void setValue(String field, Double value) {
        switch(field.toLowerCase()) {
            case("ft"):
                ft = value;
                break;
            case("hs"):
                hs = value;
                break;
            case("ks"):
                ks = value;
                break;
            case("ls"):
                ls = value;
                break;
            case("lt"):
                lt = value;
                break;
            case("ms"):
                ms = value;
                break;
            case("mt"):
                mt = value;
                break;
            case("pt"):
                pt = value;
                break;
            case("rs"):
                rs = value;
                break;
            case("rt"):
                rt = value;
                break;
            case("st"):
                st = value;
                break;
            case("tt"):
                tt = value;
                break;
            case("us"):
                us = value;
                break;
            case("vt"):
                vt = value;
                break;
            case("xt"):
                xt = value;
                break;
            case("zt"):
                zt = value;
                break;
            default: throw new IllegalArgumentException("Unknown colorant " + field);
        }
    }
}
