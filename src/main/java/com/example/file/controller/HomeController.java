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

    @GetMapping("/egc")
    public String egc() {
        return "templates/egc.html";
    }

    @GetMapping("/contact")
    public String contact() {
        return "templates/contact.html";
    }

    @GetMapping("/display-data")
    public String display() {
        return "templates/display-data.html";
    }


}
