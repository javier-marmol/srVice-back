package com.javier.srvice.file.infrastructure.controller.dto;

import com.javier.srvice.file.domain.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileOutputDto {
    private Integer id;
    private String fileName;
    private String ubication;
    private String downloadLink;

    public FileOutputDto(File file){
        this.setId(file.getId());
        this.setFileName(file.getFileName());
        this.setUbication(file.getUbication());
        this.setDownloadLink(file.getDownloadLink());
    }
}
