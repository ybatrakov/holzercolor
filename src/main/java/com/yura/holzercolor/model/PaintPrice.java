package com.yura.holzercolor.model;

import lombok.Getter;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "paint_prices")
@Getter
public class PaintPrice implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

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
}
