package com.javier.srvice.comment.application.port;

import com.javier.srvice.comment.domain.Comment;
import com.javier.srvice.comment.infrastructure.controller.dto.input.CommentInputDto;

import java.util.List;

public interface CommentServicePort {
    Comment comment(CommentInputDto commentInputDto, Integer idJob, String emailAuth) throws Exception;
    Comment update(CommentInputDto commentInputDto, Integer idComment, String emailAuth) throws Exception;
    List<Comment> getClientComments(Integer idClient) throws Exception;
    List<Comment> getEmployeeComments(Integer idEmployee) throws Exception;
}
