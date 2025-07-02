package hr.spring.web.trisek.controller.rest;

import hr.spring.web.trisek.model.Order;
import hr.spring.web.trisek.model.OrderItem;
import hr.spring.web.trisek.service.ItemService;
import hr.spring.web.trisek.service.OrderService;
import hr.spring.web.trisek.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderHistoryController {

    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping("/history")
    public String orderHistory(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Integer userId = userService.findByUsername(username).map(u -> u.getId()).orElse(null);
        if (userId == null) return "redirect:/login";

        List<Order> orders = orderService.findByUserId(userId);
        model.addAttribute("orders", orders);
        return "order-history";
    }

    @GetMapping("/details/{orderId}")
    public String orderDetails(@PathVariable Integer orderId, Model model) {
        Optional<Order> orderOpt = orderService.getById(orderId);
        if (orderOpt.isEmpty()) return "redirect:/orders/history";
        Order order = orderOpt.get();

        List<OrderItem> items = orderService.findItemsByOrderId(orderId);

        List<Map<String, Object>> itemDetails = new ArrayList<>();
        for (OrderItem item : items) {
            Map<String, Object> map = new HashMap<>();
            map.put("quantity", item.getQuantity());
            map.put("price", item.getPrice());
            itemService.getById(item.getItemId().longValue()).ifPresent(dto -> {
                map.put("name", dto.getName());
                map.put("description", dto.getDescription());
            });
            itemDetails.add(map);
        }

        model.addAttribute("order", order);
        model.addAttribute("itemDetails", itemDetails);
        return "order-details";
    }
}
