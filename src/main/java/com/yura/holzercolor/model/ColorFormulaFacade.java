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

    public static final Set<String> FIELDS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("ba", "be", "je", "jo", "jp", "lt", "ma", "na", "op", "ov", "ra", "rp", "rv", "tt", "vi", "vr")));

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
        ret.put("ra", ra);
        ret.put("rp", rp);
        ret.put("rv", rv);
        ret.put("tt", tt);
        ret.put("vi", vi);
        ret.put("vr", vr);

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
    public void setValue(String field, Double value) {
        // Reflection?
        switch(field.toLowerCase()) {
            case "ba" :
                ba = value;
                break;
            case "be" :
                be = value;
                break;
            case "je" :
                je = value;
                break;
            case "jo" :
                jo = value;
                break;
            case "ma" :
                ma = value;
                break;
            case "na" :
                na = value;
                break;
            case "ov" :
                ov = value;
                break;
            case "ra" :
                ra = value;
                break;
            case "rv" :
                rv = value;
                break;
            case "vi" :
                vi = value;
                break;
            case "vr" :
                vr = value;
                break;
            case "op" :
                op = value;
                break;
            case "jp" :
                jp = value;
                break;
            case "rp" :
                rp = value;
                break;
            case "tt" :
                tt = value;
                break;
            case "lt" :
                lt = value;
                break;
            default:
                throw new IllegalArgumentException("Unknown colorant " + field);
        }
    }
}
