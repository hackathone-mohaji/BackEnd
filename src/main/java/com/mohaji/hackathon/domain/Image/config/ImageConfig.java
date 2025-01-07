package com.mohaji.hackathon.domain.Image.config;


import java.util.List;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ImageConfig {

    @Getter
    private static String imageDirectory;
    @Getter
    private static List<String> allowedExtensions;
    @Getter
    private static long maxFileSize;

    @Value("${image.directory}")
    public void setImageDirectory(String imageDirectory) {
        ImageConfig.imageDirectory = imageDirectory;
    }

    @Value("${image.allowed-extensions}")
    public void setAllowedExtensions(List<String> allowedExtensions) {
        ImageConfig.allowedExtensions = allowedExtensions;
    }

    @Value("${image.max-file-size}")
    public void setMaxFileSize(long maxFileSize) {
        ImageConfig.maxFileSize = maxFileSize;
    }
}
