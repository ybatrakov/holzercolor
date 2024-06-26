package com.yura.holzercolor.model;

import lombok.Getter;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "paint_prices_facade")
@Getter
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
    private Double vt;
    private Double rp;
    private Double rv;
    private Double tt;
    private Double vi;
    private Double vr;
}
