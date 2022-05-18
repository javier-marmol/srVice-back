package com.javier.srvice.comment.domain;

import com.javier.srvice.comment.infrastructure.controller.dto.input.CommentInputDto;
import com.javier.srvice.job.domain.Job;
import com.javier.srvice.security.domain.User;
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
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "body")
    private String body;

    @Column(name = "comment_date")
    private Date commentDate;

    @Column(name = "type")
    private String type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_commenter", referencedColumnName = "id")
    private User userCommenter;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_commented", referencedColumnName = "id")
    private User userCommented;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_job", referencedColumnName = "id")
    private Job job;

    public Comment(CommentInputDto comment){
        this.setCommentDate(comment.getCommentDate());
        this.setBody(comment.getBody());
    }
}
