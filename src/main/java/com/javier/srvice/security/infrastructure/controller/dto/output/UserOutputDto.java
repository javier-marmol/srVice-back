package com.javier.srvice.security.infrastructure.controller.dto.output;

import com.javier.srvice.security.domain.Rol;
import com.javier.srvice.security.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String linkImage;

    public UserOutputDto(User user){
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setRols(user.getRols());
        if(user.getImage()!=null) this.setLinkImage(user.getImage().getDownloadLink());
    }
}
