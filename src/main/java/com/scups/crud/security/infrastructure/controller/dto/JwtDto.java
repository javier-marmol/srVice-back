package com.scups.crud.security.infrastructure.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtDto {
    private String token;
    private String bearer = "Bearer";
    private String name;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String jwt, String username, Collection<? extends GrantedAuthority> authorities) {
        this.setToken(jwt);
        this.setName(username);
        this.setAuthorities(authorities);
    }
}
