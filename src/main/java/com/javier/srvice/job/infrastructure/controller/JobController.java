package com.javier.srvice.job.infrastructure.controller;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.controller.dto.output.EmployeeOutputDto;
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
        Job job = jobServicePort.create(jobInputDto, principal.getName());
        return new JobOutputDto(job);
    }

    @PutMapping("/update/{id}")
    public JobOutputDto update(@RequestBody JobInputDto jobInputDto, @PathVariable("id") Integer id, Principal principal) throws Exception {
        Job job = jobServicePort.update(jobInputDto, id, principal.getName());
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
    @PutMapping("/clientDefineAsFinished/{idJob}")
    public JobOutputDto clientDefineAsFinished(@PathVariable("idJob") Integer idJob, Principal principal) throws Exception{
        Job job = jobServicePort.clientDefineAsFinished(idJob, principal.getName());
        return new JobOutputDto(job);
    }
    @PutMapping("/employeeDefineAsFinished/{idJob}")
    public JobOutputDto employeeDefineAsFinished(@PathVariable("idJob") Integer idJob, Principal principal) throws Exception{
        Job job = jobServicePort.employeeDefineAsFinished(idJob, principal.getName());
        return new JobOutputDto(job);
    }
    @GetMapping("/findByCity/{city}")
    public List<JobOutputDto> findByCity(@PathVariable("city") String city, Principal principal) throws Exception {
        List<Job> jobs = jobServicePort.findByCity(city, principal.getName());
        List<JobOutputDto> jobsToReturn = jobs.stream().map(JobOutputDto::new).collect(Collectors.toList());
        return jobsToReturn;
    }
    @GetMapping("findByClient/{idClient}")
    public List<JobOutputDto> findByClient(@PathVariable("idClient") Integer idClient, Principal principal) throws Exception {
        List<Job> jobs = jobServicePort.findByClient(idClient, principal.getName());
        return jobs.stream().map(JobOutputDto::new).collect(Collectors.toList());
    }
    @GetMapping("getSelectedCandidateByJob/{idJob}")
    public EmployeeOutputDto getSelectedCandidateByJob(@PathVariable Integer idJob) throws Exception {
        Employee employee = jobServicePort.getSelectedCandidate(idJob);
        return new EmployeeOutputDto(employee);
    }
}
