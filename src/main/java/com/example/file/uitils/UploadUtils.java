package com.example.file.uitils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Component
public class UploadUtils {

    public String saveFile(MultipartFile multipartFile, String filePath) {
        String originalName = UUID.randomUUID() + multipartFile.getOriginalFilename();
        File file = new File(filePath + originalName);
        try {
            if (file.exists()) {
                file.delete();
            }
            if (!originalName.equals("")) {
                multipartFile.transferTo(file);
                return originalName;
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }



}
