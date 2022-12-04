package com.example.file.api;


import com.example.file.dto.DataModel;
import com.example.file.processor.DataProcessor;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/data")
@AllArgsConstructor
public class DataApi {

    DataProcessor dataProcessor;

    @GetMapping("/{user_id}")
    public List<DataModel> get(@PathVariable(name = "user_id") Long userId) throws IOException {
        return dataProcessor.get(userId);
    }
}
