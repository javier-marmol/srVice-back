package com.javier.srvice.job.infrastructure.controller;

import com.javier.srvice.job.application.port.JobServicePort;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.controller.dto.input.JobInputDto;
import com.javier.srvice.job.infrastructure.controller.dto.output.JobOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobServicePort jobServicePort;

    @PostMapping("/create")
    public JobOutputDto create(@RequestBody JobInputDto jobInputDto, Principal principal) throws Exception {
        Job job = jobServicePort.create(new Job(jobInputDto), jobInputDto.getIdClient(), principal.getName());
        return new JobOutputDto(job);
    }

    @PutMapping("/update/{id}")
    public JobOutputDto update(@RequestBody JobInputDto jobInputDto, @PathVariable("id") Integer id, Principal principal) throws Exception {
        Job job = jobServicePort.update(new Job(jobInputDto), id, principal.getName());
        return new JobOutputDto(job);
    }
    @GetMapping("/findAll")
    public List<JobOutputDto> findAll(){
        List<Job> jobs = jobServicePort.findAll();
        List<JobOutputDto> jobsOutputDto = jobs.stream().map(JobOutputDto::new).collect(Collectors.toList());
        return jobsOutputDto;
    }
    @GetMapping("/find/{id}")
    public JobOutputDto findById(@PathVariable("id") Integer id) throws Exception {
        Job job = jobServicePort.findById(id);
        return new JobOutputDto(job);
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Principal principal) throws Exception{
        jobServicePort.delete(id, principal.getName());
        return "Deleted job with id: " + id;
    }
}
