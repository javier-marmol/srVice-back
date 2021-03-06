package com.javier.srvice.comment.infrastructure.repository;

import com.javier.srvice.comment.domain.Comment;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa extends JpaRepository<Comment, Integer> {
    List<Comment> findByJob(Job job);
    List<Comment> findByUserCommentedAndType(User user, String type);
    Boolean existsByJobAndUserCommenter(Job job, User userCommenter);
}
