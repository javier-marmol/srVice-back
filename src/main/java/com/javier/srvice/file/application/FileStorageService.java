package com.javier.srvice.file.application;

import com.javier.srvice.file.application.port.FileStoragePort;
import com.javier.srvice.file.domain.File;
import com.javier.srvice.file.configuration.FileStorageProperties;
import com.javier.srvice.file.infrastructure.repository.FileRepositoryJpa;
import com.javier.srvice.security.domain.User;
import com.javier.srvice.security.infrastructure.repository.UserRepositoryJpa;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService implements FileStoragePort {

    @Autowired
    private FileRepositoryJpa fileRepositoryJpa;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) throws Exception {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        }catch(Exception ex){
            throw new Exception("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public File storeFile(MultipartFile file, String emailAuth) throws Exception {
        User user = userRepositoryJpa.findByEmail(emailAuth).orElseThrow(() ->new Exception("That user does not exists"));
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try{
            if(originalFileName.contains("..")) throw new Exception("Sorry! Filename contains invalid path sequence "+ originalFileName);
            if(!checkIfImage(extension)) throw new Exception("It looks like that file it is not an image");
            String fileName = UUID.randomUUID().toString()+"."+extension;
            Path targetLocation =this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/getFile/").path(fileName).toUriString();
            File fileToSave = new File(fileName, fileDownloadUrl,fileStorageLocation.toString(), originalFileName, user);
            fileRepositoryJpa.save(fileToSave);
            return fileToSave;
        }catch (Exception e){
            throw new Exception("Could not store file "+ originalFileName +". Please try again!", e);
        }
    }

    @Override
    public void deleteFile(Integer idFile) throws Exception {
    File file = fileRepositoryJpa.findById(idFile).orElseThrow(() -> new Exception("That file does not exists"));
        try{
            Path targetLocation =this.fileStorageLocation.resolve(file.getFileName());
            Files.deleteIfExists(targetLocation);
            fileRepositoryJpa.delete(file);
        }catch (Exception e){
            throw new Exception("Could not find file");
        }
    }
    @Override
    public Resource loadElementsAsResource(String fileName) throws Exception {
        Path path = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(path.toUri());
        return resource;
    }

    public Boolean checkIfImage(String extension){
        extension = extension.toLowerCase();
        if(extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")) return true;
        return false;
    }
}
