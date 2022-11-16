package com.example.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {

    @GetMapping("/home")
    public String homeAdmin() {
        return "templates/index.html";
    }

    @GetMapping("/display-data")
    public String display() {
        return "templates/display-data.html";
    }


}
