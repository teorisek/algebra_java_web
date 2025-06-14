package hr.spring.web.trisek.service;

import hr.spring.web.trisek.dto.CategoryDTO;
import hr.spring.web.trisek.model.Category;
import hr.spring.web.trisek.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.getAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        return toDto(categoryRepository.save(toCategory(categoryDTO)));
    }

    @Override
    public Optional<CategoryDTO> getById(long id) { return categoryRepository.getById((int) id).map(this::toDto);    }

    @Override
    public boolean delete(long id) {
        return categoryRepository.delete((int) id);
    }

    private CategoryDTO toDto(Category category) {
        return new CategoryDTO(category.getName(), category.getDescription());
    }

    private Category toCategory(CategoryDTO dto) {
        return new Category(null, dto.getName(),dto.getDescription());
    }
}
