package com.javier.srvice.comment.infrastructure.controller.dto.output;


import com.javier.srvice.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentOutputDto {

    private Integer id;
    private String body;
    private Date commentDate;
    private Integer idUserCommenter;
    private Integer idUserCommented;

    public CommentOutputDto(Comment comment){
        this.setId(comment.getId());
        this.setBody(comment.getBody());
        this.setCommentDate(comment.getCommentDate());
        this.setIdUserCommenter(comment.getUserCommenter().getId());
        this.setIdUserCommented(comment.getUserCommented().getId());
    }
}
