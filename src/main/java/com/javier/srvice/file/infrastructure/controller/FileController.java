package com.javier.srvice.file.infrastructure.controller;

import com.javier.srvice.file.application.port.FileStoragePort;
import com.javier.srvice.file.domain.File;
import com.javier.srvice.file.infrastructure.controller.dto.FileOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.core.io.Resource;
import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileStoragePort fileStoragePort;

    @PostMapping("/uploadFile")
    public FileOutputDto uploadFile(@RequestParam("file")MultipartFile file, Principal principal) throws Exception {
        File fileToSave = fileStoragePort.storeFile(file, principal.getName());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileToSave.getFileName()).toUriString();
        return new FileOutputDto(fileToSave);
    }
    @DeleteMapping("/deleteFile/{idFile}")
    public String deleteFile(@PathVariable("idFile")Integer idFile) throws Exception {
        fileStoragePort.deleteFile(idFile);
        return "Deleted file";
    }
    @GetMapping("/getFile/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName) throws Exception {
        Resource resource = fileStoragePort.loadElementsAsResource(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
