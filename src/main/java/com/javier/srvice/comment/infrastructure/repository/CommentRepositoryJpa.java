package com.javier.srvice.comment.infrastructure.repository;

import com.javier.srvice.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositoryJpa extends JpaRepository<Comment, Integer> {
}
