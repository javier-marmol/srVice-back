package com.javier.srvice.security.infrastructure.controller.dto.auth;

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
    private String email;
    private String name;
    private String linkImage;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String jwt, String username, Collection<? extends GrantedAuthority> authorities, String email, String linkImgae) {
        this.setToken(jwt);
        this.setName(username);
        this.setAuthorities(authorities);
        this.setEmail(email);
        this.setLinkImage(linkImgae);
    }
}
