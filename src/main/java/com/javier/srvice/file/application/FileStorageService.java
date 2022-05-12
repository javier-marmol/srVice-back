package com.javier.srvice.file.application;

import com.javier.srvice.file.application.port.FileStoragePort;
import com.javier.srvice.file.domain.FileStorageProperties;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService implements FileStoragePort {

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
    public String storeFile(MultipartFile file) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try{
            if(fileName.contains("..")) throw new Exception("Sorry! Filename contains invalid path sequence "+ fileName);
            if(!checkIfImage(extension)) throw new Exception("It looks like that file it is not an image");
            Path targetLocation =this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        }catch (Exception e){
            throw new Exception("Could not store file "+ fileName +". Please try again!", e);
        }
    }

    @Override
    public String deleteFile(String fileName) throws Exception {
        try{
            if(fileName.contains("..")) throw new Exception("Sorry! Filename contains invalid path sequence "+ fileName);
            Path targetLocation =this.fileStorageLocation.resolve(fileName);
            Files.delete(targetLocation);
            return fileName;
        }catch (Exception e){
            throw new Exception("Could not find file "+ fileName +". Please try again!", e);
        }
    }
    public Boolean checkIfImage(String extension){
        if(extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")) return true;
        return false;
    }
}
