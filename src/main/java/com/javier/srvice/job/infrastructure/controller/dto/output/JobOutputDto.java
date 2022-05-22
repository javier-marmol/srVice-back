package com.javier.srvice.job.infrastructure.controller.dto.output;

import com.javier.srvice.job.domain.Job;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.output.PresentedToOutputDto;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.output.SimplePresentedToOutputDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

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

    private String city;

    private String description;

    private String linkImage;

    private List<SimplePresentedToOutputDto> candidates;

    public JobOutputDto(Job job){
        this.setId(job.getId());
        this.setName(job.getName());
        this.setCategory(job.getCategory());
        this.setPrice(job.getPrice());
        this.setSearchingCandidate(job.getSearchingCandidate());
        if(job.getClient()!=null)this.setIdClient(job.getClient().getId());
        this.setInProgress(job.getInProgress());
        this.setCity(job.getCity());
        this.setDescription(job.getDescription());
        if(job.getJogImage()!=null) this.setLinkImage(job.getJogImage().getDownloadLink());
        this.setCandidates(job.getCandidates().stream().map(SimplePresentedToOutputDto::new).collect(Collectors.toList()));
    }
}
