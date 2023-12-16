package com.example.db_mai.controller;

import com.example.db_mai.dto.cart.ProductCount;
import com.example.db_mai.model.Component;
import com.example.db_mai.service.ComponentService;
import com.example.db_mai.util.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public String getComponentsPage(@RequestParam(name = "searchKeyword", required = false) String searchKeyword,
                                     Model model) {
        List<Component> components = componentService.search(searchKeyword);
        model.addAttribute("components", components);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("cart", cart);
        return "components";
    }

    @PostMapping("/updateQuantity")
    public ResponseEntity<Integer> updateQuantity(@RequestParam("action") String action,
                                         @RequestParam("componentId") Long componentId) {
        var item = cart.getOrDefault(componentId, new ProductCount(componentId, 0));
        if ("INC".equals(action)) {
            item.setQuantity(item.getQuantity() + 1);
            cart.put(item.getItemId(), item);
        } else if ("DEC".equals(action)) {
            int quantity = item.getQuantity() - 1;
            if (quantity <= 0) {
                item.setQuantity(0);
                cart.remove(item.getItemId());
            } else {
                item.setQuantity(quantity);
                cart.put(item.getItemId(), item);
            }
        }

        return new ResponseEntity<>(item.getQuantity(), HttpStatus.OK);
    }
}