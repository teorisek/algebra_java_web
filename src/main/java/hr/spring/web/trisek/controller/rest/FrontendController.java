package hr.spring.web.trisek.controller;

import hr.spring.web.trisek.dto.ItemDTO;
import hr.spring.web.trisek.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FrontendController {

    private final ItemService itemService;

    public FrontendController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String items(Model model) {
        List<ItemDTO> items = itemService.getAll();
        model.addAttribute("items", items);
        return "items";
    }
}
