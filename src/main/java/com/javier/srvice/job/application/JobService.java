package com.javier.srvice.job.application;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.client.infrastructure.repository.ClientRepositoryJpa;
import com.javier.srvice.job.application.port.JobServicePort;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.controller.dto.input.JobInputDto;
import com.javier.srvice.job.infrastructure.repository.JobRepositoryJpa;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.security.domain.User;
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

    public Job create(JobInputDto jobInputDto, String emailAuth) throws Exception, ELException {
        Job job = new Job(jobInputDto);
        Client client = clientRepositoryJpa.findById(jobInputDto.getIdClient()).orElseThrow(() -> new Exception("That client does not exists"));
        AuthUtil.checkAuth(client.getUser(), emailAuth);
        job.setClient(client);
        jobRepositoryJpa.save(job);
        return job;
    }


    public Job update(Job job, Integer id, String emailAuth) throws Exception {
        AuthUtil.checkAuth(job.getClient().getUser(), emailAuth);
        job.setId(id);
        jobRepositoryJpa.save(job);
        return job;
    }

    public List<Job> findAll(){
        return jobRepositoryJpa.findAll();
    }


    public Job findById(Integer id) throws Exception {
        Optional<Job> jobOptional = jobRepositoryJpa.findById(id);
        if(jobOptional.isEmpty()) throw new Exception("That job doesn't exists");
        return jobOptional.get();
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
}
