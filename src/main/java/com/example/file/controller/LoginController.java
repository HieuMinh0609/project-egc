package com.example.file.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @GetMapping("/login")
    public String homeAdmin() {
        return "templates/login.html";
    }
}
