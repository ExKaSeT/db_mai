package com.example.db_mai.controller;

import com.example.db_mai.model.Component;
import com.example.db_mai.security.UserInfoDetails;
import com.example.db_mai.service.ComponentService;
import com.example.db_mai.service.OrderService;
import com.example.db_mai.util.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final ComponentService componentService;
    private final Cart cart;
    private final OrderService orderService;

    @GetMapping
    public String getPage(Model model) {
        List<Component> components = cart.keySet().stream().map(componentService::get).toList();
        model.addAttribute("components", components);
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping
    public String createOrder(Model model, @AuthenticationPrincipal UserInfoDetails user) {
        if (cart.isEmpty()) {
            model.addAttribute("errors", List.of("Корзина пуста"));
            return "cart";
        }
        var order = orderService.create(user.getCustomer().getId(), cart);
        return "redirect:/orders/" + order.getId();
    }
}
