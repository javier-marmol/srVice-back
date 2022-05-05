package com.javier.srvice.presentedTo.application;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.repository.EmployeeRepositoryJpa;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.repository.JobRepositoryJpa;
import com.javier.srvice.presentedTo.application.port.PresentedToServicePort;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.input.PresentedToInputDto;
import com.javier.srvice.presentedTo.infrastructure.repository.PresentedToRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresentedToService implements PresentedToServicePort {

    @Autowired
    private PresentedToRepositoryJpa presentedToRepositoryJpa;

    @Autowired
    private EmployeeRepositoryJpa employeeRepositoryJpa;

    @Autowired
    private JobRepositoryJpa jobRepositoryJpa;


    @Override
    public PresentedTo presentTo(PresentedTo presentedTo, Integer idEmployee, Integer idJob) throws Exception {
        Job job = jobRepositoryJpa.findById(idJob).orElseThrow(() -> new Exception("Cannot present to a job that doesn't exist"));
        Employee employee = employeeRepositoryJpa.findById(idEmployee).orElseThrow(() -> new Exception("Cannot present to a job if you are not an employee"));
        if(job.getInProgress()==true) throw new Exception("Cannot present to a job that is already in progress");
        if(job.getSearchingCandidate()!=true) throw new Exception("Cannot present to a job that is not searching for candidates");
        presentedTo.setEmployee(employee);
        presentedTo.setJob(job);
        presentedToRepositoryJpa.save(presentedTo);
        return presentedTo;
    }

}

