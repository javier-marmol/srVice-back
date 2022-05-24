package com.javier.srvice.security.aplication;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.client.infrastructure.repository.ClientRepositoryJpa;
import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.repository.EmployeeRepositoryJpa;
import com.javier.srvice.file.application.port.FileStoragePort;
import com.javier.srvice.file.domain.File;
import com.javier.srvice.file.infrastructure.repository.FileRepositoryJpa;
import com.javier.srvice.security.aplication.port.RolServicePort;
import com.javier.srvice.security.aplication.port.UserServicePort;
import com.javier.srvice.security.domain.Rol;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import com.javier.srvice.shared.enums.RolName;
import com.javier.srvice.sms.domain.Sms;
import com.javier.srvice.sms.infrastructure.infrastructure.SmsRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserServicePort {

    @Autowired
    UserRepositoryJpa userRepositoryJpa;

    @Autowired
    RolServicePort rolServicePort;

    @Autowired
    private FileStoragePort fileStoragePort;

    @Autowired
    private FileRepositoryJpa fileRepositoryJpa;

    @Autowired
    private SmsRepositoryJpa smsRepositoryJpa;

    @Autowired
    private EmployeeRepositoryJpa employeeRepositoryJpa;

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    public Optional<User> getByEmail(String email){
        return userRepositoryJpa.findByEmail(email);
    }

    public boolean existsByName(String name){
        return userRepositoryJpa.existsByName(name);
    }

    public boolean existsByEmail(String email){
        return userRepositoryJpa.existsByEmail(email);
    }

    public void create(User user){
        userRepositoryJpa.save(user);
    }

    public User getInfo(Integer idUser) {
        return userRepositoryJpa.findById(idUser).get();
    }

    public void makeUserEmployee(User user) {
        Set<Rol> roles = user.getRols();
        Rol rol = rolServicePort.getByRolName(RolName.ROLE_EMPLOYEE).get();
        roles.add(rol);
        user.setRols(roles);
        create(user);
    }

    public User setProfileImage(Integer idFile, String emailAuth) throws Exception {
        User user = userRepositoryJpa.findByEmail(emailAuth).orElseThrow(() -> new Exception("That user does not exists"));
        File file = fileRepositoryJpa.findById(idFile).orElseThrow(() -> new Exception("That file does not exists"));
        if(user.getImage()!=null) fileStoragePort.deleteFile(user.getImage().getId());
        user.setImage(file);
        userRepositoryJpa.save(user);
        return user;
    }

    @Override
    public User verificate(String email, String code) throws Exception {
        User userToCheck = getByEmail(email).get();
        if(userToCheck.getVerified()) throw new Exception("You are already verfied");
        Sms sms = smsRepositoryJpa.findByPhoneNumber(userToCheck.getPhoneNumber());
        if(sms==null)
            throw new Exception("First you must send a verification SMS");
        if(sms.getExpiration().compareTo(new Date())<0)
            throw new Exception("Your SMS has expired. Try to request another one.");
        if(!sms.getCode().equals(code))
            throw new Exception("The verification codes does not match");
        userToCheck.setVerified(true);
        User userToReturn = userRepositoryJpa.save(userToCheck);
        smsRepositoryJpa.delete(sms);
        return userToReturn;
    }

    @Override
    public User getByEmployee(Integer idEmployee) throws Exception {
        Employee employee = employeeRepositoryJpa.findById(idEmployee).orElseThrow(()-> new Exception("That employee does not exists."));
        return employee.getUser();
    }

    @Override
    public User getByClient(Integer idClient) throws Exception {
        Client client = clientRepositoryJpa.findById(idClient).orElseThrow(()-> new Exception("That employee does not exists."));
        return client.getUser();
    }
}

