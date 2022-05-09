package com.javier.srvice.comment.application;

import com.javier.srvice.comment.application.port.CommentServicePort;
import com.javier.srvice.comment.domain.Comment;
import com.javier.srvice.comment.infrastructure.repository.CommentRepositoryJpa;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.job.infrastructure.repository.JobRepositoryJpa;
import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentService implements CommentServicePort {

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private JobRepositoryJpa jobRepositoryJpa;

    @Override
    public Comment comment(Comment comment, Integer idJob, Integer idUserCommenter, Integer idUserCommented) throws Exception {
        Job job = jobRepositoryJpa.findById(idJob).orElseThrow(() -> new Exception("That job does not exists"));
        if(job.getInProgress()||job.getSearchingCandidate()) throw new Exception("Cannot comment if the job is not finished yet");

    }
}
