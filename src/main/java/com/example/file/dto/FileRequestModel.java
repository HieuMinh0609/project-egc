package com.example.file.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileRequestModel {

    private MultipartFile multipartFile;

}
