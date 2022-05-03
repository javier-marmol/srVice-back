package com.javier.srvice.job.domain;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.job.infrastructure.controller.dto.input.JobInputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "job")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private Double price;

    @Column(name = "searching_candidate")
    private Boolean searchingCandidate;

   @Column(name = "in_progress")
   private Boolean inProgress;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    public Job(JobInputDto jobInputDto){
        this.setPrice(jobInputDto.getPrice());
        this.setCategory(jobInputDto.getCategory());
        this.setName(jobInputDto.getName());
        this.setSearchingCandidate(jobInputDto.getSearchingCandidate());
        this.setInProgress(jobInputDto.getInProgress());
    }
}
