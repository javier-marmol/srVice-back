package com.javier.srvice.file.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileStorageProperties {
    private String uploadDir;
}
