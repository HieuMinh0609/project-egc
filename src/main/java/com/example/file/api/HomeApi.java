package com.example.file.api;


import com.example.file.dto.FileRequestModel;
import com.example.file.processor.EcgProcessor;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class HomeApi {

    private EcgProcessor processor;

    @PostMapping("/import")
    public Long saveData(@ModelAttribute FileRequestModel fileRequestModel) throws Exception {
        return processor.uploadFileExcel(fileRequestModel);
    }
}


