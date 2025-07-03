package hr.spring.web.trisek.controller.rest;

import hr.spring.web.trisek.model.Category;
import hr.spring.web.trisek.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoryAdminController {

    private final CategoryRepository categoryRepository;

    public CategoryAdminController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryRepository.getAll();
        model.addAttribute("categories", categories);
        return "category";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "category-create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute("category") Category category) {
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<Category> categoryOpt = categoryRepository.getById(id);
        if (categoryOpt.isPresent()) {
            model.addAttribute("category", categoryOpt.get());
            return "category-edit";
        } else {
            return "redirect:/admin/category";
        }
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") Integer id, @ModelAttribute("category") Category category) {
        category.setId(id);
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }


    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        categoryRepository.delete(id);
        return "redirect:/admin/category";
    }
}
