package com.javier.srvice.comment.infrastructure.controller.dto.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentInputDto {
    private String body;
    private Integer idUserCommenter;
    private Integer idUserCommented;
    private Date commentDate = new Date();
}
