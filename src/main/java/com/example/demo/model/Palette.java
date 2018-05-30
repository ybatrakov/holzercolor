package com.example.demo.model;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "palettes")
public class Palette implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    private String rgb;

    @ManyToOne
    private PaletteType paletteType;

    @ManyToMany(mappedBy = "palettes")
    private Set<Paint> paints = new HashSet<>();

    @ManyToMany(mappedBy = "palettesFacade")
    private Set<Paint> paintsFacade = new HashSet<>();
    
    public Integer getId() {
        return id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getRGB() {
        return rgb;
    }

    public void setRGB(String rgb) {
        this.rgb = rgb;
    }

    public PaletteType getType() {
        return paletteType;
    }
}
