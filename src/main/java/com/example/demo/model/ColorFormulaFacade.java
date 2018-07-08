package com.example.demo.model;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "color_formulas_facade")
@JsonSerialize(using = ColorFormulaSerializer.class)
public class ColorFormulaFacade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Paint paint;

    @ManyToOne
    private Palette palette;

    @ManyToOne
    private Base base;

    private double ba;
    private double be;
    private double je;
    private double jo;
    private double jp;
    private double lt;
    private double ma;
    private double na;
    private double op;
    private double ov;
    private double ra;
    private double rp;
    private double rv;
    private double tt;
    private double vi;
    private double vr;

    public Integer getId() {
        return id;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    public double getBa() {
        return ba;
    }

    public double getBe() {
        return be;
    }

    public double getJe() {
        return je;
    }

    public double getJo() {
        return jo;
    }

    public double getJp() {
        return jp;
    }

    public double getLt() {
        return lt;
    }

    public double getMa() {
        return ma;
    }

    public double getNa() {
        return na;
    }

    public double getOp() {
        return op;
    }

    public double getOv() {
        return ov;
    }

    public double getRa() {
        return ra;
    }

    public double getRp() {
        return rp;
    }

    public double getRv() {
        return rv;
    }

    public double getTt() {
        return tt;
    }

    public double getVi() {
        return vi;
    }

    public double getVr() {
        return vr;
    }

    public void setColorants(Map<String, Double> values) {
        for(Map.Entry<String, Double> e : values.entrySet()) {
            double price = e.getValue();
                
            // Reflection?
            switch(e.getKey().toLowerCase()) {
            case "ba" :
                ba = price;
                break;
            case "be" :
                be = price;
                break;
            case "je" :
                je = price;
                break;
            case "jo" :
                jo = price;
                break;
            case "ma" :
                ma = price;
                break;
            case "na" :
                na = price;
                break;
            case "ov" :
                ov = price;
                break;
            case "ra" :
                ra = price;
                break;
            case "rv" :
                rv = price;
                break;
            case "vi" :
                vi = price;
                break;
            case "vr" :
                vr = price;
                break;
            case "op" :
                op = price;
                break;
            case "jp" :
                jp = price;
                break;
            case "rp" :
                rp = price;
                break;
            case "tt" :
                tt = price;
                break;
            case "lt" :
                lt = price;
                break;
            default:
                throw new IllegalArgumentException("Unknown colorant " + e.getKey());
            }
        }
    }
}
