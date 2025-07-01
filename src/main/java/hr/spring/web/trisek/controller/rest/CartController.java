package hr.spring.web.trisek.controller.rest;

import hr.spring.web.trisek.model.Cart;
import hr.spring.web.trisek.service.ItemService;
import hr.spring.web.trisek.dto.ItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import hr.spring.web.trisek.model.Checkout;

import java.util.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final Cart cart;
    private final ItemService itemService;

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

//    @GetMapping("/checkout")
//    public String checkoutForm(Model model) {
//        double total = calculateTotal(cart.getItems());
//        Checkout checkout = new Checkout();
//        checkout.setAmount(total);
//        model.addAttribute("checkout", checkout);
//        return "checkout-form";
//    }
//
//    @PostMapping("/checkout")
//    public String processCheckout(@ModelAttribute Checkout checkout, Model model) {
//        checkout.setPaymentStatus("PENDING");
//        Checkout saved = checkoutService.save(checkout);
//        cart.clear();
//
//        if ("PAYPAL".equalsIgnoreCase(saved.getPaymentMethod())) {
//            return "redirect:/checkout/paypal?amount=" + saved.getAmount() + "&id=" + saved.getId();
//        } else {
//            model.addAttribute("message", "Order placed! Please pay in person.");
//            return "checkout-success";
//        }
//    }
//
//    private double calculateTotal(Map<Integer, Integer> cartItems) {
//        double total = 0.0;
//        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
//            int itemId = entry.getKey();
//            int quantity = entry.getValue();
//            Optional<Item> itemOpt = itemService.getById((long) itemId);
//            if (itemOpt.isPresent()) {
//                total += itemOpt.get().getPrice() * quantity;
//            }
//        }
//        return total;
//    }

    @GetMapping("/checkout")
    public String checkoutRedirect() {
        return "redirect:/checkout";
    }
}
