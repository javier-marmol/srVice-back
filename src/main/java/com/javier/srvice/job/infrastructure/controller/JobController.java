package com.javier.srvice.job.infrastructure.controller;

import com.javier.srvice.job.application.port.JobServicePort;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.controller.dto.input.JobInputDto;
import com.javier.srvice.job.infrastructure.controller.dto.output.JobOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobServicePort jobServicePort;

    @PostMapping("/create")
    public JobOutputDto create(@RequestBody JobInputDto jobInputDto) throws Exception {
        Job job = jobServicePort.create(new Job(jobInputDto), jobInputDto.getIdClient());
        return new JobOutputDto(job);
    }

}
