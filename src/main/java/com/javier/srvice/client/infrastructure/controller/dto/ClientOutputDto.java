package com.javier.srvice.client.infrastructure.controller.dto;

import com.javier.srvice.client.domain.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientOutputDto {
    private Integer id;
    private Integer idUser;

    public ClientOutputDto(Client client){
        this.setId(client.getId());
        this.setIdUser(client.getUser().getId());
    }
}
