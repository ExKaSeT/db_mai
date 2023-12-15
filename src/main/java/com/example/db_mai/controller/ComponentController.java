package com.example.db_mai.controller;

import com.example.db_mai.dto.cart.ProductCount;
import com.example.db_mai.model.Component;
import com.example.db_mai.service.ComponentService;
import com.example.db_mai.util.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/components")
@RequiredArgsConstructor
public class ComponentController {

    private final ComponentService componentService;
    private final Cart cart;

    @GetMapping
    public String showComponentsPage(@RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                                     Model model) {
        List<Component> components = componentService.search(searchKeyword);
        model.addAttribute("components", components);
        model.addAttribute("searchKeyword", searchKeyword);
        return "components";
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestParam("componentId") Long componentId) {
        var item = cart.getOrDefault(componentId, new ProductCount(componentId, 0));
        item.setQuantity(item.getQuantity() + 1);
        return "redirect:/components";
    }
}