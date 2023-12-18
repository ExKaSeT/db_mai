package com.example.db_mai.controller;

import com.example.db_mai.dto.customer.CustomerInfoDto;
import com.example.db_mai.security.UserInfoDetails;
import com.example.db_mai.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/profile")
    public String getProfilePage(Model model, @AuthenticationPrincipal UserInfoDetails user) {
        model.addAttribute("customerInfoDto", new CustomerInfoDto(user.getCustomer().getName()));
        return "profile";
    }

    @GetMapping("/me")
    public String getInfoPage(Model model, @AuthenticationPrincipal UserInfoDetails user) {
        model.addAttribute("customerInfoDto", new CustomerInfoDto(user.getCustomer().getName()));
        return "me";
    }

    @PostMapping("/me")
    public String updateInfo(@ModelAttribute("customerInfoDto") @Valid CustomerInfoDto customerInfoDto,
                           BindingResult result, @AuthenticationPrincipal UserInfoDetails user) {
        if (result.hasErrors()) {
            return "me";
        }

        customerService.update(user.getCustomer().getId(), customerInfoDto.getName());
        user.getCustomer().setName(customerInfoDto.getName());

        return "redirect:/me";
    }
}
