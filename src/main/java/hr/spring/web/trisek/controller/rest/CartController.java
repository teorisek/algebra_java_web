package hr.spring.web.trisek.controller.rest;

import hr.spring.web.trisek.model.Cart;
import hr.spring.web.trisek.model.CheckoutForm;
import hr.spring.web.trisek.model.Order;
import hr.spring.web.trisek.model.OrderItem;
import hr.spring.web.trisek.service.ItemService;
import hr.spring.web.trisek.service.OrderService;
import hr.spring.web.trisek.service.UserService;
import hr.spring.web.trisek.model.User;
import hr.spring.web.trisek.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final Cart cart;
    private final ItemService itemService;
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping("/add")
    public String addToCart(@RequestParam int itemId, @RequestParam(defaultValue = "1") int quantity) {
        cart.addItem(itemId, quantity);
        return "redirect:/items";
    }

    @GetMapping("/view")
    public String viewCart(Model model) {
        Map<Integer, Integer> cartItems = cart.getItems();
        List<ItemDTO> items = new ArrayList<>();
        for (Integer id : cartItems.keySet()) {
            itemService.getById(id.longValue()).ifPresent(items::add);
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("items", items);
        return "cart";
    }

    @PostMapping("/update")
    public String updateCart(@RequestParam int itemId, @RequestParam int quantity) {
        cart.updateItem(itemId, quantity);
        return "redirect:/cart/view";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam int itemId) {
        cart.removeItem(itemId);
        return "redirect:/cart/view";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cart.clear();
        return "redirect:/cart/view";
    }

    @GetMapping("/checkout")
    public String showCheckoutForm(Model model) {
        BigDecimal total = calculateCartTotal(cart.getItems());
        CheckoutForm checkoutForm = new CheckoutForm();
        checkoutForm.setAmount(total);
        model.addAttribute("checkoutForm", checkoutForm);
        return "checkout-form";
    }

    @PostMapping("/checkout")
    public String processCheckout(@ModelAttribute CheckoutForm checkoutForm) {
        BigDecimal total = calculateCartTotal(cart.getItems());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        Integer userId = userOpt.get().getId();

        Order order = new Order(
                checkoutForm.getCustomerName(),
                checkoutForm.getEmail(),
                checkoutForm.getAddress(),
                total,
                checkoutForm.getPaymentMethod(),
                "PENDING"
        );
        order.setOrderDate(OffsetDateTime.now());
        order.setUserId(userId);

        Set<OrderItem> orderItems = new HashSet<>();
        for (Map.Entry<Integer, Integer> entry : cart.getItems().entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            Optional<ItemDTO> itemOpt = itemService.getById((long) itemId);
            if (itemOpt.isPresent()) {
                BigDecimal price = itemOpt.get().getPrice();
                OrderItem orderItem = new OrderItem(order, itemId, quantity, price);
                orderItems.add(orderItem);
            }
        }
        order.setItems(orderItems);
        orderService.save(order);
        cart.clear();

        if ("PAYPAL".equals(checkoutForm.getPaymentMethod())) {
            return "redirect:/paypal?orderId=" + order.getId();
        }
        return "checkout-success";
    }

    private BigDecimal calculateCartTotal(Map<Integer, Integer> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            Optional<ItemDTO> itemOpt = itemService.getById((long) itemId);
            if (itemOpt.isPresent()) {
                BigDecimal price = itemOpt.get().getPrice();
                if (price != null) {
                    total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
                }
            }
        }
        return total;
    }
}
