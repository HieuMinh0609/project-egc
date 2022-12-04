package com.example.file.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/data")
public class DataController {

    @GetMapping("/{id}/history")
    public String display(Model model, @PathVariable Long id) {
        model.addAttribute("userId", id);
        return "templates/user-history.html";
    }
}
