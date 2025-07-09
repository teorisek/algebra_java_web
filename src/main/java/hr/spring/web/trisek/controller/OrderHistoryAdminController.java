package hr.spring.web.trisek.controller;

import hr.spring.web.trisek.model.Order;
import hr.spring.web.trisek.model.OrderItem;
import hr.spring.web.trisek.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class OrderHistoryAdminController {

    private final OrderService orderService;

    @GetMapping("/all-orders")
    public String allOrders(Model model) {
        List<Order> orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "all-orders";
    }

    @GetMapping("/all-order-details/{orderId}")
    public String orderDetails(@PathVariable Integer orderId, Model model) {
        Optional<Order> orderOpt = orderService.getById(orderId);
        if (orderOpt.isEmpty()) return "redirect:/admin/all-orders";
        Order order = orderOpt.get();

        List<OrderItem> items = orderService.findItemsByOrderId(orderId);

        model.addAttribute("order", order);
        model.addAttribute("items", items);
        return "all-order-details";
    }
}