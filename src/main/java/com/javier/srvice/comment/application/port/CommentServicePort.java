package com.javier.srvice.comment.application.port;

import com.javier.srvice.comment.domain.Comment;
import com.javier.srvice.comment.infrastructure.controller.dto.input.CommentInputDto;

public interface CommentServicePort {
    Comment comment(CommentInputDto commentInputDto, Integer idJob, String emailAuth) throws Exception;
    Comment update(CommentInputDto commentInputDto, Integer idComment, String emailAuth) throws Exception;
}
