package com.example.demo.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "color_formulas")
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

    private Double ft;
    private Double hs;
    private Double ks;
    private Double ls;
    private Double lt;
    private Double ms;
    private Double mt;
    private Double pt;
    private Double rs;
    private Double rt;
    private Double st;
    private Double tt;
    private Double us;
    private Double vt;
    private Double xt;
    private Double zt;

    public Base getBase() {
        return base;
    }

    public Integer getId() {
        return id;
    }

    public Double getFt() {
        return ft;
    }

    public Double getHs() {
        return hs;
    }

    public Double getKs() {
        return ks;
    }

    public Double getLs() {
        return ls;
    }

    public Double getLt() {
        return lt;
    }

    public Double getMs() {
        return ms;
    }

    public Double getMt() {
        return mt;
    }

    public Double getPt() {
        return pt;
    }

    public Double getRs() {
        return rs;
    }

    public Double getRt() {
        return rt;
    }

    public Double getSt() {
        return st;
    }

    public Double getTt() {
        return tt;
    }

    public Double getUs() {
        return us;
    }

    public Double getVt() {
        return vt;
    }

    public Double getXt() {
        return xt;
    }

    public Double getZt() {
        return zt;
    }

}
