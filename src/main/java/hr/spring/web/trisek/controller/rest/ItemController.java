package hr.spring.web.trisek.controller.rest;

import hr.spring.web.trisek.dto.CategoryDTO;
import hr.spring.web.trisek.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/rest/item")
public class ItemController {

    private final CategoryService categoryService;

    public ItemController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public List<CategoryDTO> getAll() {
    return categoryService.getAll();
    }

    @PostMapping("/")
    public CategoryDTO save(@RequestBody CategoryDTO category) {
    return categoryService.save(category);
    }

    @GetMapping("/{id}")
    public Optional<CategoryDTO> getById(@PathVariable int id) {
        return categoryService.getById(id);
    }

    @DeleteMapping("/")
    public boolean delete(@RequestBody long id) { return categoryService.delete(id);}
}
