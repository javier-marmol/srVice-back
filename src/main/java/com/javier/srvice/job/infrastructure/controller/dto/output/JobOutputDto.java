package com.javier.srvice.job.infrastructure.controller.dto.output;

import com.javier.srvice.job.domain.Job;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobOutputDto {
    private Integer id;

    private String name;

    private String category;

    private Double price;

    private Boolean searchingCandidate;

    private Integer idClient;

    private Boolean inProgress;

    public JobOutputDto(Job job){
        this.setId(job.getId());
        this.setName(job.getName());
        this.setCategory(job.getCategory());
        this.setPrice(job.getPrice());
        this.setSearchingCandidate(job.getSearchingCandidate());
        if(job.getClient()!=null)this.setIdClient(job.getClient().getId());
        this.setInProgress(job.getInProgress());
    }
}
