package com.example.db_mai.controller;

import com.example.db_mai.security.UserInfoDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/components")
public class ComponentController {

    @GetMapping
    public String getPage(@AuthenticationPrincipal UserInfoDetails userDetails) {
        return "components";
    }
}
