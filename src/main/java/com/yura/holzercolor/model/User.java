package com.yura.holzercolor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(name = "users_roles",
               joinColumns = { @JoinColumn(name = "user_id") },
               inverseJoinColumns = { @JoinColumn(name = "role_id") }
               )
    private Set<UserRole> roles;

    @OneToOne
    private UserProfile profile;

    @NotBlank
    @Column(name="encrypted_password")
    private String password;

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

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
