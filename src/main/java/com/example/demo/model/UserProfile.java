package com.example.demo.model;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "profiles")
public class UserProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PaintPrice paintPrice;

    @ManyToOne
    private PaintPriceFacade paintPriceFacade;

    @NotBlank
    private Integer fee;

    public Integer getId() {
        return id;
    }

    public PaintPrice getPaintPrice() {
	return paintPrice;
    }

    public PaintPriceFacade getPaintPriceFacade() {
        return paintPriceFacade;
    }

    public Integer getFee() {
        return fee;
    }
}
