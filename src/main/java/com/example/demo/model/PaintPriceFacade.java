package com.example.demo.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "paint_prices_facade")
public class PaintPriceFacade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Double ba;
    private Double be;
    private Double je;
    private Double jo;
    private Double jp;
    private Double lt;
    private Double ma;
    private Double na;
    private Double op;
    private Double ov;
    private Double ra;
    private Double rp;
    private Double rv;
    private Double tt;
    private Double vi;
    private Double vr;

    public Integer getId() {
        return id;
    }

    public String name() {
        return name;
    }

    public Double getBa() {
        return ba;
    }

    public Double getBe() {
        return be;
    }

    public Double getJe() {
        return je;
    }

    public Double getJo() {
        return jo;
    }

    public Double getJp() {
        return jp;
    }

    public Double getLt() {
        return lt;
    }

    public Double getMa() {
        return ma;
    }

    public Double getNa() {
        return na;
    }

    public Double getOp() {
        return op;
    }

    public Double getOv() {
        return ov;
    }

    public Double getRa() {
        return ra;
    }

    public Double getRp() {
        return rp;
    }

    public Double getRv() {
        return rv;
    }

    public Double getTt() {
        return tt;
    }

    public Double getVi() {
        return vi;
    }

    public Double getVr() {
        return vr;
    }
}
