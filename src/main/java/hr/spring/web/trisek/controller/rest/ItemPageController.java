package hr.spring.web.trisek.controller.rest;

import hr.spring.web.trisek.dto.ItemDTO;
import hr.spring.web.trisek.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemPageController {

    private final ItemService itemService;

    public ItemPageController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String listItems(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            Model model
    ) {
        List<ItemDTO> items = itemService.getAllFiltered(name, category);
        List<String> categories = itemService.getAll()
                .stream()
                .map(ItemDTO::getCategoryName)
                .distinct()
                .toList();
        model.addAttribute("items", items);
        model.addAttribute("categories", categories);
        return "items";
    }
}
