package com.javier.srvice.file.infrastructure;

import com.javier.srvice.file.application.port.FileStoragePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@CrossOrigin
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileStoragePort fileStoragePort;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
        String fileName = fileStoragePort.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();
        return fileDownloadUri;
    }
    @DeleteMapping("/deleteFile/{fileName}")
    public String deleteFile(@PathVariable("fileName")String file) throws Exception {
        String fileName = fileStoragePort.deleteFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();
        return fileDownloadUri;
    }
}
