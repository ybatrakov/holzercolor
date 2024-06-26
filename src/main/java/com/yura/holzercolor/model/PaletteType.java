package com.yura.holzercolor.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@Table(name = "palette_types")
public class PaletteType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    @NotBlank
    private String name;

    @Setter
    @NotBlank
    private String nick;

    @Setter
    @NotBlank
    private String shortName;

    @ManyToMany
    @JoinTable(name="formula_types_palette_types",
        joinColumns = @JoinColumn(name = "palette_type_id"),
        inverseJoinColumns = @JoinColumn(name = "formula_type_id"))
    private Collection<FormulaType> formulaType;

}
