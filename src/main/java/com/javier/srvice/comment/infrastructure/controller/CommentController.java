package com.javier.srvice.comment.infrastructure.controller;

import com.javier.srvice.comment.application.port.CommentServicePort;
import com.javier.srvice.comment.domain.Comment;
import com.javier.srvice.comment.infrastructure.controller.dto.input.CommentInputDto;
import com.javier.srvice.comment.infrastructure.controller.dto.output.CommentOutputDto;
import com.javier.srvice.shared.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    private CommentServicePort commentServicePort;

    @PostMapping("/{idJob}")
    public CommentOutputDto comment(@PathVariable("idJob") Integer idJob, @RequestBody CommentInputDto commentInputDto, Principal principal) throws Exception {
        Comment comment = commentServicePort.comment(commentInputDto, idJob, principal.getName());
        return new CommentOutputDto(comment);
    }
    @PutMapping("/update/{idComment}")
    public CommentOutputDto update(@PathVariable("idComment") Integer idComment, @RequestBody CommentInputDto commentInputDto, Principal principal) throws Exception {
        Comment comment = commentServicePort.update(commentInputDto, idComment, principal.getName());
        return new CommentOutputDto(comment);
    }
}
