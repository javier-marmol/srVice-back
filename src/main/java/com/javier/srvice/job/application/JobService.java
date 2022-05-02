package com.javier.srvice.job.application;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.client.infrastructure.repository.ClientRepositoryJpa;
import com.javier.srvice.job.application.port.JobServicePort;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.repository.JobRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class JobService implements JobServicePort {

    @Autowired
    private JobRepositoryJpa jobRepositoryJpa;

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    public Job create(Job job, Integer idClient) throws Exception {
        Optional<Client> client = clientRepositoryJpa.findById(idClient);
        if(client.get()==null) throw new Exception("That client doesn't exist");
        job.setClient(client.get());
        jobRepositoryJpa.save(job);
        return job;
    }
}
