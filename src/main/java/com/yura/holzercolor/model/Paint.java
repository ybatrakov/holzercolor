package com.yura.holzercolor.model;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
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

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "color_formulas",
               joinColumns = { @JoinColumn(name = "paint_id") },
               inverseJoinColumns = { @JoinColumn(name = "palette_id") }
               )
    private Set<Palette> palettes = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "color_formulas_facade",
               joinColumns = { @JoinColumn(name = "paint_id") },
               inverseJoinColumns = { @JoinColumn(name = "palette_id") }
               )
    private Set<Palette> palettesFacade = new HashSet<>();
    
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
