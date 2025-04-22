package com.mybury.waver.util;

import static com.mybury.waver.util.DateTimeUtils.now;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileUploadUtils {

    private final Path uploadPath;

    public FileUploadUtils(@Value("${waver.upload.path}") String uploadPath) {
        this.uploadPath = Paths.get(uploadPath).toAbsolutePath().normalize();
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
        String fileName = now() + "_" + originalFilename;
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        Path targetDir = uploadPath.resolve(datePath).normalize();

        try {
            Files.createDirectories(targetDir);
            Path targetLocation = targetDir.resolve(fileName);
            file.transferTo(targetLocation.toFile());
            return targetLocation.toString();
        } catch (IOException ex) {
            log.error("Could not upload file: {}", originalFilename, ex);
        }
        return null;
    }
}
