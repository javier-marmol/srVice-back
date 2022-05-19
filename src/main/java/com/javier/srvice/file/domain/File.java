package com.javier.srvice.file.domain;

import com.javier.srvice.security.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "file", schema="public")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "download_link")
    private String downloadLink;

    @Column(name = "ubication")
    private String ubication;

    @Column(name = "original_file_name")
    private String originalFileName;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;


    public File(String fileName, String downloadLink, String ubication, String originalFileName, User user){
        this.setFileName(fileName);
        this.setDownloadLink(downloadLink);
        this.setUbication(ubication);
        this.setOriginalFileName(originalFileName);
        this.setUser(user);
    }
}
