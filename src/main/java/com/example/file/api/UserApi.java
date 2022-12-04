package com.example.file.api;

import com.example.file.dto.UserModel;
import com.example.file.processor.UserProcessor;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserApi {

    UserProcessor dataProcessor;

    @GetMapping("/{id}")
    public UserModel get(@PathVariable(name = "id") Long userId) throws IOException {
        return dataProcessor.get(userId);
    }

    @GetMapping("/list")
    public Page<UserModel> page(@RequestParam(required = false) String keyword
            , @RequestParam(defaultValue = "1") Integer page
            , @RequestParam(defaultValue = "10") Integer size) throws IOException {
        return dataProcessor.get(keyword, page, size);
    }

}
