package com.example.demo.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "paint_prices")
public class PaintPrice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
