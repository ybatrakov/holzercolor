package com.example.demo.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "paints")
public class Paint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String brand;

    @NotBlank
    private String name;

    private String density;

    private String factor;

    @OneToOne
    private Base base;

    @OneToOne
    private Packing packing;

    public Integer getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getFactor() {
        return factor;
    }

    public void setFactor(String factor) {
        this.factor = factor;
    }

    public Base getBase() {
        return base;
    }

    public Packing getPacking() {
        return packing;
    }
}
