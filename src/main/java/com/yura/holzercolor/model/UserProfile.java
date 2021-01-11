package com.yura.holzercolor.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @NotNull
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

    public void setFee(Integer fee) {
        this.fee = fee;
    }
}
