package com.javier.srvice.job.application;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.client.infrastructure.repository.ClientRepositoryJpa;
import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.repository.EmployeeRepositoryJpa;
import com.javier.srvice.file.domain.File;
import com.javier.srvice.file.infrastructure.repository.FileRepositoryJpa;
import com.javier.srvice.job.application.port.JobServicePort;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.controller.dto.input.JobInputDto;
import com.javier.srvice.job.infrastructure.repository.JobRepositoryJpa;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import com.javier.srvice.shared.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.el.ELException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class JobService implements JobServicePort {

    @Autowired
    private JobRepositoryJpa jobRepositoryJpa;

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private FileRepositoryJpa fileRepositoryJpa;

    public Job create(JobInputDto jobInputDto, String emailAuth) throws Exception, ELException {
        Job job = new Job(jobInputDto);
        Client client = clientRepositoryJpa.findById(jobInputDto.getIdClient()).orElseThrow(() -> new Exception("That client does not exists"));
        File file = fileRepositoryJpa.findById(jobInputDto.getIdFile()).orElseThrow(() -> new Exception("That file does not exists"));
        AuthUtil.checkAuth(client.getUser(), emailAuth);
        job.setJogImage(file);
        if(client.getUser().getId()!=file.getUser().getId()) throw new Exception("You are not the owner of the file");
        job.setClient(client);
        jobRepositoryJpa.save(job);
        return job;
    }


    public Job update(JobInputDto jobInputDto, Integer id, String emailAuth) throws Exception {
        Job job = new Job(jobInputDto);
        Client client = clientRepositoryJpa.findById(jobInputDto.getIdClient()).orElseThrow(() -> new Exception("That client does not exists"));
        File file = fileRepositoryJpa.findById(jobInputDto.getIdFile()).orElseThrow(() -> new Exception("That file does not exists"));
        job.setJogImage(file);
        job.setClient(client);
        AuthUtil.checkAuth(client.getUser(), emailAuth);
        job.setId(id);
        jobRepositoryJpa.save(job);
        return job;
    }

    public List<Job> findAll(){
        return jobRepositoryJpa.findAll();
    }


    public Job findById(Integer id) throws Exception {
        Job job = jobRepositoryJpa.findById(id).orElseThrow(() -> new Exception("That job doesn't exists"));
        return job;
    }

    public void delete(Integer id, String emailAuth) throws Exception {
        Job job = jobRepositoryJpa.findById(id).orElseThrow(() -> new Exception("That job doesn't exists"));
        AuthUtil.checkAuth(job.getClient().getUser(), emailAuth);
        if (job.getInProgress()) throw new Exception("Can't delete a job that has started");
        jobRepositoryJpa.delete(job);
    }
    public Job clientDefineAsFinished(Integer idJob, String emailAuth) throws Exception{
        Job job = jobRepositoryJpa.findById(idJob).orElseThrow(() -> new Exception("That job does not exists"));
        AuthUtil.checkAuth(job.getClient().getUser(), emailAuth);
        if(!job.getInProgress() || job.getSearchingCandidate()) throw new Exception("Can't declare as finished a job that has not started");
        job.setClientDeclareAsFinished(true);
        if(job.getClientDeclareAsFinished() && job.getEmployeeDeclareAsFinished()) job.setInProgress(false);
        jobRepositoryJpa.save(job);
        return job;
    }
    public Job employeeDefineAsFinished(Integer idJob, String emailAuth) throws Exception{
        Job job = jobRepositoryJpa.findById(idJob).orElseThrow(() -> new Exception("That job does not exists"));
        if(!job.getInProgress() || job.getSearchingCandidate()) throw new Exception("Can't declare as finished a job that has not started");
        PresentedTo presentedTo = job.getCandidates().stream().filter(candidate -> candidate.getSelected()).findFirst().get();
        AuthUtil.checkAuth(presentedTo.getEmployee().getUser(), emailAuth);
        job.setEmployeeDeclareAsFinished(true);
        if(job.getClientDeclareAsFinished() && job.getEmployeeDeclareAsFinished()) job.setInProgress(false);
        jobRepositoryJpa.save(job);
        return job;
    }

    @Override
    public List<Job> findByCity(String city, String emailAuth) throws Exception {
        User user = userRepositoryJpa.findByEmail(emailAuth).orElseThrow(() -> new Exception("That user does not exists"));
        List<Job> jobs = jobRepositoryJpa.findBySearchingCandidateTrueAndCity(city);
        return jobs.stream().filter(e -> e.getClient().getUser().getId()!=user.getId()).collect(Collectors.toList());
    }
}
