package com.javier.srvice.comment.infrastructure.repository;

import com.javier.srvice.comment.domain.Comment;
import com.javier.srvice.job.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepositoryJpa extends JpaRepository<Comment, Integer> {
    Optional<Comment> findByJob(Job job);
}
