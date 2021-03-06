package com.javier.srvice.job.domain;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.file.domain.File;
import com.javier.srvice.job.infrastructure.controller.dto.input.JobInputDto;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "job", schema="public")
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

    @Column(name = "description")
    @NotNull
    private String description;

    @Column(name = "city")
    @NotNull
    private String city;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "job")
    private List<PresentedTo> candidates;

    @Column(name = "client_declare_as_finished")
    private Boolean clientDeclareAsFinished;

    @Column(name = "employee_declare_as_finished")
    private Boolean employeeDeclareAsFinished;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_file_image", referencedColumnName = "id")
    private File jogImage;

    public Job(JobInputDto jobInputDto){
        this.setPrice(jobInputDto.getPrice());
        this.setCategory(jobInputDto.getCategory());
        this.setName(jobInputDto.getName());
        this.setSearchingCandidate(jobInputDto.getSearchingCandidate());
        this.setInProgress(jobInputDto.getInProgress());
        this.setClientDeclareAsFinished(false);
        this.setEmployeeDeclareAsFinished(false);
        this.setCity(jobInputDto.getCity().toLowerCase());
        this.setDescription(jobInputDto.getDescription());
    }
}
