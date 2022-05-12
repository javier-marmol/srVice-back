package com.javier.srvice.security.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> rols = new HashSet<>();
    @Column(name="image")
    private String image;


    public User(String name, String email, String encode) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(encode);
    }

    public User(String name, String email, String encode, Set<Rol> roles) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(encode);
        this.setRols(roles);
    }
}
