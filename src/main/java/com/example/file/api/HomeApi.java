package com.example.file.api;


import com.example.file.dto.EcgModel;
import com.example.file.dto.FileRequestModel;
import com.example.file.processor.EcgProcessor;
import groovyjarjarpicocli.CommandLine;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class HomeApi {

    private EcgProcessor processor;

    @PostMapping("/import")
    public String saveData(@ModelAttribute FileRequestModel fileRequestModel) throws Exception {
        return processor.uploadFileExcel(fileRequestModel);
    }

    @GetMapping("/read")
    public List<EcgModel> readData(@RequestParam String nameFile) throws IOException {
        return processor.readFileExcel(nameFile);
    }


}
