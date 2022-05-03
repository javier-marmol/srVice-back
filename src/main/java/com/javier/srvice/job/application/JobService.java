package com.javier.srvice.job.application;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.client.infrastructure.repository.ClientRepositoryJpa;
import com.javier.srvice.job.application.port.JobServicePort;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.repository.JobRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.el.ELException;
import java.util.List;
import java.util.Optional;


@Service
public class JobService implements JobServicePort {

    @Autowired
    private JobRepositoryJpa jobRepositoryJpa;

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    public Job create(Job job, Integer idClient) throws Exception, ELException {
        Optional<Client> client = clientRepositoryJpa.findById(idClient);
        if(client.get()==null) throw new Exception("That client doesn't exist");
        job.setClient(client.get());
        jobRepositoryJpa.save(job);
        return job;
    }

    public Job update(Job job, Integer id) throws Exception {
        Optional<Job> jobOptional = jobRepositoryJpa.findById(id);
        if(jobOptional.isEmpty()) throw new Exception("That job doesn't exists");
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

    public void delete(Integer id) throws Exception {
        Optional<Job> jobOptional = jobRepositoryJpa.findById(id);
        if(jobOptional.isEmpty()) throw new Exception("That job doesn't exists");
        if (jobOptional.get().getInProgress()) throw new Exception("Can't delete a job that has started");
        jobRepositoryJpa.delete(jobOptional.get());
    }
}
