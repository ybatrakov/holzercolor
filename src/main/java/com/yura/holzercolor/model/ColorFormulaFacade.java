package com.yura.holzercolor.model;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "color_formulas_facade")
@JsonSerialize(using = ColorFormulaSerializer.class)
public class ColorFormulaFacade implements Formula, Serializable {
    @Id
    @SequenceGenerator(name="color_formulas_facade_seq", sequenceName = "color_formulas_facade_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "color_formulas_facade_seq")
    private Integer id;

    @ManyToOne
    private Paint paint;

    @ManyToOne
    private Palette palette;

    @ManyToOne
    private Base base;

    @Column
    private double ba;

    @Column
    private double be;

    @Column
    private double je;

    @Column
    private double jo;

    @Column
    private double jp;

    @Column
    private double lt;

    @Column
    private double ma;

    @Column
    private double na;

    @Column
    private double op;

    @Column
    private double ov;

    @Column
    private double rp;

    @Column
    private double rv;

    @Column
    private double tt;

    @Column
    private double vi;

    @Column
    private double vr;

    @Column
    private double vt;

    @Override
    public Map<String, Double> getValues() {
        HashMap<String, Double> ret = new HashMap<>();
        ret.put("ba", ba);
        ret.put("be", be);
        ret.put("je", je);
        ret.put("jo", jo);
        ret.put("jp", jp);
        ret.put("lt", lt);
        ret.put("ma", ma);
        ret.put("na", na);
        ret.put("op", op);
        ret.put("ov", ov);
        ret.put("rp", rp);
        ret.put("rv", rv);
        ret.put("tt", tt);
        ret.put("vi", vi);
        ret.put("vr", vr);
        ret.put("vt", vt);

        return ret;
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
    public void setValues(Map<String, Double> values) {
        values.forEach((colorant, value) -> {
            // Reflection?
            switch(colorant.toLowerCase()) {
                case "ba":
                    ba = value;
                    break;
                case "be":
                    be = value;
                    break;
                case "je":
                    je = value;
                    break;
                case "jo":
                    jo = value;
                    break;
                case "ma":
                    ma = value;
                    break;
                case "na":
                    na = value;
                    break;
                case "ov":
                    ov = value;
                    break;
                case "rv":
                    rv = value;
                    break;
                case "vi":
                    vi = value;
                    break;
                case "vr":
                    vr = value;
                    break;
                case "op":
                    op = value;
                    break;
                case "jp":
                    jp = value;
                    break;
                case "rp":
                    rp = value;
                    break;
                case "tt":
                    tt = value;
                    break;
                case "lt":
                    lt = value;
                    break;
                case "vt":
                    vt = value;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown colorant " + colorant);
            }});
    }
}
