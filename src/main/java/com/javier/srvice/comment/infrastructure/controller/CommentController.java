package com.javier.srvice.comment.infrastructure.controller;

import com.javier.srvice.comment.application.port.CommentServicePort;
import com.javier.srvice.comment.domain.Comment;
import com.javier.srvice.comment.infrastructure.controller.dto.input.CommentInputDto;
import com.javier.srvice.comment.infrastructure.controller.dto.output.CommentOutputDto;
import com.javier.srvice.shared.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("getClientComments/{idClient}")
    public List<CommentOutputDto> getClientComments(@PathVariable("idClient") Integer idCLient) throws Exception {
        List<Comment> comments = commentServicePort.getClientComments(idCLient);
        return comments.stream().map(CommentOutputDto::new).collect(Collectors.toList());
    }
    @GetMapping("getEmployeeComments/{idEmployee}")
    public List<CommentOutputDto> getEmployeeComments(@PathVariable("idEmployee") Integer idEmployee) throws Exception {
        List<Comment> comments = commentServicePort.getEmployeeComments(idEmployee);
        return comments.stream().map(CommentOutputDto::new).collect(Collectors.toList());
    }
    @GetMapping("checkIfCommented/{idJob}/{idUser}")
    public ResponseEntity<?> checkIfCommented(@PathVariable("idJob")Integer idJob, @PathVariable("idUser") Integer idUser) throws Exception {
        return new ResponseEntity<Boolean>(commentServicePort.checkIfCommented(idJob, idUser), HttpStatus.OK);
    }
}
