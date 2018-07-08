package com.example.demo.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "color_formulas")
@JsonSerialize(using = ColorFormulaSerializer.class)
public class ColorFormula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Paint paint;

    @ManyToOne
    private Palette palette;

    @ManyToOne
    private Base base;

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

    public Base getBase() {
        return base;
    }

    public Integer getId() {
        return id;
    }

    public double getFt() {
        return ft;
    }

    public double getHs() {
        return hs;
    }

    public double getKs() {
        return ks;
    }

    public double getLs() {
        return ls;
    }

    public double getLt() {
        return lt;
    }

    public double getMs() {
        return ms;
    }

    public double getMt() {
        return mt;
    }

    public double getPt() {
        return pt;
    }

    public double getRs() {
        return rs;
    }

    public double getRt() {
        return rt;
    }

    public double getSt() {
        return st;
    }

    public double getTt() {
        return tt;
    }

    public double getUs() {
        return us;
    }

    public double getVt() {
        return vt;
    }

    public double getXt() {
        return xt;
    }

    public double getZt() {
        return zt;
    }
}
