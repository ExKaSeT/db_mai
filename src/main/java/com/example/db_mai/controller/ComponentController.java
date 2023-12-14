package com.example.db_mai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kek")
public class ComponentController {

    @GetMapping
    public String getPage() {
        return "components";
    }
}
