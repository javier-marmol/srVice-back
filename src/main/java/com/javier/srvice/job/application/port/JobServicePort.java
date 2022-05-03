package com.javier.srvice.job.application.port;

import com.javier.srvice.job.domain.Job;

import javax.el.ELException;
import java.util.List;

public interface JobServicePort {
    Job create(Job job, Integer idClient) throws Exception;
    Job update(Job job, Integer id) throws Exception;
    List<Job> findAll();
    Job findById(Integer id) throws Exception;
    void delete(Integer id) throws Exception;
}
