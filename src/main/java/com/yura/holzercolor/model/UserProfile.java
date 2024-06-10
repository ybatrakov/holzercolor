package com.yura.holzercolor.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "profiles")
@Getter
public class UserProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PaintPrice paintPrice;

    @ManyToOne
    private PaintPriceFacade paintPriceFacade;

    @NotNull
    @Setter
    private Integer fee;
}
