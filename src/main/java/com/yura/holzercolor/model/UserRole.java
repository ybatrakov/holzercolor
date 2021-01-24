package com.yura.holzercolor.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "roles")
public class UserRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Integer getId() {
        return id;
    }

    public String getName() {
	return name;
    }
}
