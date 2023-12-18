package com.example.db_mai.controller;

import com.example.db_mai.security.UserInfoDetails;
import com.example.db_mai.service.OrderService;
import com.example.db_mai.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ReceiptService receiptService;

    @GetMapping("/{id}")
    @PreAuthorize("@orderSecurity.isAllowedToModifyOrder(authentication, #id)")
    public String getOrder(Model model, @PathVariable Long id) {
        var order = orderService.get(id);
        var componentDtos = orderService.getComponentOrderInfo(order.getComponentOrderList());
        double cost = componentDtos.stream().mapToDouble(dto -> dto.getPrice() * dto.getQuantity()).sum();
        model.addAttribute("components", componentDtos);
        model.addAttribute("cost", cost);
        model.addAttribute("orderName", "Заказ #" + order.getId());
        return "order";
    }

    @GetMapping("/{id}/receipt")
    @PreAuthorize("@orderSecurity.isAllowedToModifyOrder(authentication, #id)")
    public ResponseEntity<byte[]> getReceipt(@PathVariable Long id) {
        var order = orderService.get(id);
        var componentDtos = orderService.getComponentOrderInfo(order.getComponentOrderList());
        double cost = componentDtos.stream().mapToDouble(dto -> dto.getPrice() * dto.getQuantity()).sum();
        var bytes = receiptService.generateReceipt(componentDtos, cost, order.getOrderDate());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "receipt.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(bytes.toByteArray(), headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@orderSecurity.isAllowedToModifyOrder(authentication, #id)")
    public ResponseEntity<String> cancelOrder(@PathVariable Long id) {
        var order = orderService.get(id);
        orderService.cancel(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public String getOrders(Model model, @AuthenticationPrincipal UserInfoDetails user) {
        var orders = orderService.getAll(user.getCustomer().getId());
        model.addAttribute("orders", orders);
        return "orders";
    }
}
