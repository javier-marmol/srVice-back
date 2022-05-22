package com.javier.srvice.job.infrastructure.controller.dto.input;

import com.javier.srvice.client.domain.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

@Getter
@Setter
public class JobInputDto {
    private String name;

    private String category;

    private Double price;

    private Boolean searchingCandidate=true;

    private Integer idClient;

    private Boolean inProgress=false;

    private String city;

    private String description;

    private Integer idFile;
}
