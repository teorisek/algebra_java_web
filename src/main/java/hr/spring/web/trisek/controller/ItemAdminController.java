package hr.spring.web.trisek.controller;

import hr.spring.web.trisek.dto.ItemDTO;
import hr.spring.web.trisek.repository.CategoryRepository;
import hr.spring.web.trisek.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/item")
public class ItemAdminController {

    private final ItemService itemService;
    private final CategoryRepository categoryRepository;

    public ItemAdminController(ItemService itemService, CategoryRepository categoryRepository) {
        this.itemService = itemService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String listItems(Model model) {
        List<ItemDTO> items = itemService.getAll();
        model.addAttribute("items", items);
        return "item-admin";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new ItemDTO(null, "", "", 0, null, null, null));
        model.addAttribute("categories", categoryRepository.getAll());
        return "item-create";
    }

    @PostMapping("/create")
    public String createItem(@ModelAttribute("item") ItemDTO itemDTO) {
        itemService.save(itemDTO);
        return "redirect:/admin/item";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<ItemDTO> itemOpt = itemService.getById(id);
        if (itemOpt.isPresent()) {
            model.addAttribute("item", itemOpt.get());
            model.addAttribute("categories", categoryRepository.getAll());
            return "item-edit";
        } else {
            return "redirect:/admin/item";
        }
    }

    @PostMapping("/edit/{id}")
    public String editItem(@PathVariable("id") Integer id, @ModelAttribute("item") ItemDTO itemDTO) {
        itemDTO.setId(id);
        itemService.save(itemDTO);
        return "redirect:/admin/item";
    }

    @PostMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") Integer id) {
        itemService.delete(id);
        return "redirect:/admin/item";
    }
}
