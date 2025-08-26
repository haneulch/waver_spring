package com.mybury.waver.util;

import com.mybury.waver.common.FileProperties;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileUploadUtils {

    private final FileProperties properties;
    private final Path uploadPath;

    public FileUploadUtils(FileProperties properties) {
        this.properties = properties;
        this.uploadPath = Paths.get(properties.getUpload().getPath()).normalize();
        createDirectoriesIfNotExist();
    }


    private void createDirectoriesIfNotExist() {
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException ex) {
            log.error("Could not create upload directory: {}", uploadPath, ex);
        }
    }

    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            log.error("File is empty");
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        int dotIndex = originalFilename.lastIndexOf('.');
        String baseName = (dotIndex != -1) ? originalFilename.substring(0, dotIndex) : originalFilename;
        String extension = (dotIndex != -1) ? originalFilename.substring(dotIndex + 1) : "";

        String fileName = baseName.replaceAll("[^A-Za-z0-9]", "") + "." + extension;

        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        Path targetDir = uploadPath.resolve(datePath).normalize();

        try {
            Files.createDirectories(targetDir);
            Path targetLocation = targetDir.resolve(fileName);
            file.transferTo(targetLocation.toFile());
            return getSavedFilePath(datePath, fileName);
        } catch (IOException ex) {
            log.error("Could not upload file: {}", originalFilename, ex);
        }
        return null;
    }

    private String getSavedFilePath(String datePath, String fileName) {
        return properties.getImages().getPath() + "/" + datePath + "/" + fileName;
    }
}
