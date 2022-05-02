package com.javier.srvice.job.application.port;

import com.javier.srvice.job.domain.Job;

public interface JobServicePort {
    Job create(Job job, Integer idClient) throws Exception;
}
