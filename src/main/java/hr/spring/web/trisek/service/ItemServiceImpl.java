package hr.spring.web.trisek.service;

import hr.spring.web.trisek.dto.ItemDTO;
import hr.spring.web.trisek.model.Item;
import hr.spring.web.trisek.model.Category;
import hr.spring.web.trisek.repository.ItemRepository;
import hr.spring.web.trisek.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ItemDTO> getAll() {
        return itemRepository.getAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        Item item = toItem(itemDTO);
        Item saved = itemRepository.save(item);
        return toDto(saved);
    }

    @Override
    public Optional<ItemDTO> getById(long id) {
        return itemRepository.getById((int) id).map(this::toDto);
    }

    @Override
    public boolean delete(long id) {
        return itemRepository.delete((int) id);
    }

    private ItemDTO toDto(Item item) {
        Integer categoryId = item.getCategory().getId();
        String categoryName = categoryRepository.getById(categoryId)
                .map(Category::getName)
                .orElse(null);
        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getQuantity(),
                item.getPrice(),
                categoryId,
                categoryName
        );
    }

    private Item toItem(ItemDTO dto) {
        Category category = categoryRepository.getById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + dto.getCategoryId()));

        return new Item(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getQuantity(),
                dto.getPrice(),
                category
        );
    }
}
