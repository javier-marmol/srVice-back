package com.javier.srvice.file.application.port;

import com.javier.srvice.file.domain.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileStoragePort {
    File storeFile(MultipartFile file, String emailAuth) throws Exception;
    void deleteFile(Integer idFile) throws Exception;
}