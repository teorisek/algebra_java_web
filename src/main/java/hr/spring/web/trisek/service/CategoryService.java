package hr.spring.web.trisek.service;

import hr.spring.web.trisek.dto.CategoryDTO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryDTO> getAll();
    CategoryDTO save(CategoryDTO categoryDTO);
    Optional<CategoryDTO> getById(long id);
    boolean delete(long id);
}
