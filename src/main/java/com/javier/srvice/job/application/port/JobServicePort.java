package com.javier.srvice.job.application.port;

import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.controller.dto.input.JobInputDto;

import javax.el.ELException;
import java.util.List;

public interface JobServicePort {
    Job create(JobInputDto jobInputDto, String emailAuth) throws Exception;
    Job update(Job job, Integer id, String emailAuth) throws Exception;
    List<Job> findAll();
    Job findById(Integer id) throws Exception;
    void delete(Integer id, String emailAuth) throws Exception;
    Job clientDefineAsFinished(Integer idJob, String emailAuth) throws Exception;
    Job employeeDefineAsFinished(Integer idJob, String emailAuth) throws Exception;
    List<Job> findByCity(String city, String emailAuth) throws Exception;
}
