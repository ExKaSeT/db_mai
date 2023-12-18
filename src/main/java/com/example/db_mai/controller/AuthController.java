package com.example.db_mai.controller;

import com.example.db_mai.dto.auth.RegisterDto;
import com.example.db_mai.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("registerDto") @Valid RegisterDto registerDto, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        authService.register(registerDto.getUsername(), registerDto.getPassword());

        return "redirect:/login";
    }
}
