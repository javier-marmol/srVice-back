package com.javier.srvice.presentedTo.application;

import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.repository.EmployeeRepositoryJpa;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.repository.JobRepositoryJpa;
import com.javier.srvice.presentedTo.application.port.PresentedToServicePort;
import com.javier.srvice.presentedTo.domain.PresentedTo;
import com.javier.srvice.presentedTo.infrastructure.controller.dto.input.PresentedToInputDto;
import com.javier.srvice.presentedTo.infrastructure.repository.PresentedToRepositoryJpa;
import com.javier.srvice.shared.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PresentedToService implements PresentedToServicePort {

    @Autowired
    private PresentedToRepositoryJpa presentedToRepositoryJpa;

    @Autowired
    private EmployeeRepositoryJpa employeeRepositoryJpa;

    @Autowired
    private JobRepositoryJpa jobRepositoryJpa;


    @Override
    public PresentedTo presentTo(PresentedToInputDto presentedToInputDto, String emailAuth) throws Exception {
        PresentedTo presentedTo = new PresentedTo(presentedToInputDto);
        Job job = jobRepositoryJpa.findById(presentedToInputDto.getIdJob()).orElseThrow(() -> new Exception("Cannot present to a job that doesn't exist"));
        Employee employee = employeeRepositoryJpa.findById(presentedToInputDto.getIdEmployee()).orElseThrow(() -> new Exception("Cannot present to a job if you are not an employee"));
        Optional<PresentedTo> alreadyPresented = presentedToRepositoryJpa.findByEmployee(employee);
        if(alreadyPresented.get()!=null) throw new Exception("Cannot present to a job that you are already presented to");
        AuthUtil.checkAuth(employee.getUser(),emailAuth);
        if(job.getInProgress()==true) throw new Exception("Cannot present to a job that is already in progress");
        if(job.getSearchingCandidate()!=true) throw new Exception("Cannot present to a job that is not searching for candidates");
        presentedTo.setEmployee(employee);
        presentedTo.setJob(job);
        presentedToRepositoryJpa.save(presentedTo);
        return presentedTo;
    }

    @Override
    public PresentedTo selectCandidate(Integer idJob, Integer idEmployee, String emailAuth) throws Exception {
        Job job = jobRepositoryJpa.findById(idJob).orElseThrow(() -> new Exception("That job does not exists"));
        Employee employee = employeeRepositoryJpa.findById(idEmployee).orElseThrow(() -> new Exception("That employee does not exists"));
        AuthUtil.checkAuth(job.getClient().getUser(), emailAuth);
        PresentedTo presentedTo = presentedToRepositoryJpa.findByEmployeeAndJob(employee, job);
        presentedTo.setSelected(true);
        job.setSearchingCandidate(false);
        job.setInProgress(true);
        jobRepositoryJpa.save(job);
        presentedToRepositoryJpa.save(presentedTo);
        return presentedTo;
    }
    @Override
    public PresentedTo favourite(Integer idJob, Integer idEmployee, String emailAuth) throws Exception {
        Job job = jobRepositoryJpa.findById(idJob).orElseThrow(() -> new Exception("That job does not exists"));
        Employee employee = employeeRepositoryJpa.findById(idEmployee).orElseThrow(() -> new Exception("That employee does not exists"));
        AuthUtil.checkAuth(job.getClient().getUser(), emailAuth);
        PresentedTo presentedTo = presentedToRepositoryJpa.findByEmployeeAndJob(employee, job);
        presentedTo.setFavourite(true);
        presentedToRepositoryJpa.save(presentedTo);
        return presentedTo;
    }
    @Override
    public PresentedTo deselectFavourite(Integer idJob, Integer idEmployee, String emailAuth) throws Exception {
        Job job = jobRepositoryJpa.findById(idJob).orElseThrow(() -> new Exception("That job does not exists"));
        Employee employee = employeeRepositoryJpa.findById(idEmployee).orElseThrow(() -> new Exception("That employee does not exists"));
        AuthUtil.checkAuth(job.getClient().getUser(), emailAuth);
        PresentedTo presentedTo = presentedToRepositoryJpa.findByEmployeeAndJob(employee, job);
        presentedTo.setFavourite(false);
        presentedToRepositoryJpa.save(presentedTo);
        return presentedTo;
    }
}

