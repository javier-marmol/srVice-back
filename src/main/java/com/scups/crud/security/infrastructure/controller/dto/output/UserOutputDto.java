package com.scups.crud.security.infrastructure.controller.dto.output;

import com.scups.crud.security.domain.Rol;
import com.scups.crud.security.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOutputDto {
    private int id;
    private String name;
    private String email;
    private Set<Rol> rols = new HashSet<>();

    public UserOutputDto(User user){
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setRols(user.getRols());
    }
}
