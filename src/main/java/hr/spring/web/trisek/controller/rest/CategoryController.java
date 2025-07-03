package hr.spring.web.trisek.controller.rest;

import hr.spring.web.trisek.dto.CategoryDTO;
import hr.spring.web.trisek.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/rest/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public List<CategoryDTO> getAll() {
    return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<CategoryDTO> getById(@PathVariable int id) {
        return categoryService.getById(id);
    }

}
