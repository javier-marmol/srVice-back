package com.javier.srvice.security.domain;

import com.javier.srvice.file.domain.File;
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
@Table(name = "user", schema="public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(unique = true, name = "name")
    private String name;
    @Column(name = "email")
    @NotNull
    private String email;
    @Column(name = "password")
    @NotNull
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_rol", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> rols = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_file_image", referencedColumnName = "id")
    private File image;

    @Column(name = "verified")
    private Boolean verified;

    @Column(name = "phone_number")
    @NotNull
    private String phoneNumber;


    public User(String name, String email, String encode, String phoneNumber) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(encode);
        this.setPhoneNumber(phoneNumber);
    }

    public User(String name, String email, String encode, Set<Rol> roles) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(encode);
        this.setRols(roles);

    }
}
