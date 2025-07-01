package hr.spring.web.trisek.controller.rest;

import hr.spring.web.trisek.dto.ItemDTO;
import hr.spring.web.trisek.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/rest/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/")
    public List<ItemDTO> getAll() {
    return itemService.getAll();
    }

    @PostMapping("/")
    public ItemDTO save(@RequestBody ItemDTO item) {
    return itemService.save(item);
    }

    @GetMapping("/{id}")
    public Optional<ItemDTO> getById(@PathVariable int id) {
        return itemService.getById(id);
    }

    @DeleteMapping("/")
    public boolean delete(@RequestBody long id) { return itemService.delete(id);}
}
