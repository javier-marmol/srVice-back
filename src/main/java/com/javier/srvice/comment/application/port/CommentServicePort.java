package com.javier.srvice.comment.application.port;

import com.javier.srvice.comment.domain.Comment;

public interface CommentServicePort {
    Comment comment(Comment comment, Integer idJob, Integer idUserCommenter, Integer idUserCommented) throws Exception;
}
