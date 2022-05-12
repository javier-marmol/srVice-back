package com.javier.srvice.file.domain;

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

    public File(String fileName, String downloadLink, String ubication){
        this.setFileName(fileName);
        this.setDownloadLink(downloadLink);
        this.setUbication(ubication);
    }
}
