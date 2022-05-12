package com.javier.srvice.file.application.port;

import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {
    String storeFile(MultipartFile file) throws Exception;
    String deleteFile(String file) throws Exception;
}
