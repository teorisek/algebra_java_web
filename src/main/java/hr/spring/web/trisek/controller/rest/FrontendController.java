package hr.spring.web.trisek.controller.rest;


import hr.spring.web.trisek.dto.ItemDTO;
import hr.spring.web.trisek.service.ItemService;
import hr.spring.web.trisek.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FrontendController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    public FrontendController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

//    @GetMapping("/items")
//    public String items(Model model) {
//        List<ItemDTO> items = itemService.getAll();
//        model.addAttribute("items", items);
//        model.addAttribute("categories", categoryService.getAll());
//        return "items";
//    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
