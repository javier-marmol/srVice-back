package com.javier.srvice.comment.application;

import com.javier.srvice.client.domain.Client;
import com.javier.srvice.client.infrastructure.repository.ClientRepositoryJpa;
import com.javier.srvice.comment.application.port.CommentServicePort;
import com.javier.srvice.comment.domain.Comment;
import com.javier.srvice.comment.infrastructure.controller.dto.input.CommentInputDto;
import com.javier.srvice.comment.infrastructure.repository.CommentRepositoryJpa;
import com.javier.srvice.employee.domain.Employee;
import com.javier.srvice.employee.infrastructure.repository.EmployeeRepositoryJpa;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.repository.JobRepositoryJpa;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import com.javier.srvice.shared.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements CommentServicePort {

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private JobRepositoryJpa jobRepositoryJpa;

    @Autowired
    private ClientRepositoryJpa clientRepositoryJpa;

    @Autowired
    private EmployeeRepositoryJpa employeeRepositoryJpa;

    @Override
    public Comment comment(CommentInputDto commentInputDto, Integer idJob, String emailAuth) throws Exception {
        Job job = jobRepositoryJpa.findById(idJob).orElseThrow(() -> new Exception("That job does not exists"));
        if(commentInputDto.getIdUserCommented()==commentInputDto.getIdUserCommenter()) throw new Exception("You cannot comment yourself");
        User commenter = userRepositoryJpa.findById(commentInputDto.getIdUserCommenter()).orElseThrow(() -> new Exception("That user does not exists"));
        AuthUtil.checkAuth(commenter, emailAuth);
        checkIfAlreadyCommented(commentInputDto, job);
        if(job.getInProgress()||job.getSearchingCandidate() || !job.getClientDeclareAsFinished() || !job.getEmployeeDeclareAsFinished()) throw new Exception("Cannot comment if the job is not finished yet");
        Comment comment = new Comment(commentInputDto);
        User commented = userRepositoryJpa.findById(commentInputDto.getIdUserCommented()).orElseThrow(() -> new Exception("That user does not exists"));
        comment.setUserCommenter(commenter);
        comment.setUserCommented(commented);
        comment.setJob(job);
        setCommentType(job, comment);
        commentRepositoryJpa.save(comment);
        return comment;
    }

    @Override
    public Comment update(CommentInputDto commentInputDto, Integer idComment, String emailAuth) throws Exception {
        Comment comment = commentRepositoryJpa.findById(idComment).orElseThrow(() -> new Exception("That comment does not exists"));
        AuthUtil.checkAuth(comment.getUserCommenter(), emailAuth);
        comment.setBody(commentInputDto.getBody());
        commentRepositoryJpa.save(comment);
        return comment;
    }

    @Override
    public List<Comment> getClientComments(Integer idClient) throws Exception {
        Client client = clientRepositoryJpa.findById(idClient).orElseThrow(() -> new Exception("That client does not exists"));
        User user = client.getUser();
        List<Comment> comments = commentRepositoryJpa.findByUserCommentedAndType(user, "toClient");
        return comments;
    }

    @Override
    public List<Comment> getEmployeeComments(Integer idEmployee) throws Exception {
        Employee employee = employeeRepositoryJpa.findById(idEmployee).orElseThrow(() -> new Exception("That employee does not exists"));
        User user = employee.getUser();
        List<Comment> comments = commentRepositoryJpa.findByUserCommentedAndType(user, "toEmployee");
        return comments;
    }

    private void setCommentType(Job job, Comment comment) {
        if(job.getClient().getUser().getId()== comment.getUserCommenter().getId())
            comment.setType("toEmployee");
        else comment.setType("toClient");
    }


    private void checkIfAlreadyCommented(CommentInputDto commentInputDto, Job job) throws Exception {
        List<Comment> alreadyCommented = commentRepositoryJpa.findByJob(job);
        if(alreadyCommented.size()>0)
            for (Comment comment: alreadyCommented){
                if(comment.getUserCommenter().getId()== commentInputDto.getIdUserCommenter()){
                    throw new Exception("You have already commented");
                }
            }
    }
}
