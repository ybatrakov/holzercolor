package com.example.demo.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String email;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "users_roles",
               joinColumns = { @JoinColumn(name = "user_id") },
               inverseJoinColumns = { @JoinColumn(name = "role_id") }
               )
    private Set<UserRole> roles;

    @OneToOne
    private UserProfile profile;

    public Integer getId() {
        return id;
    }

    public String getEmail() {
	return email;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }
}
