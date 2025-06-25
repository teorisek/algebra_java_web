package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
    List<Category> getAll();
    Category save(Category category);
    boolean delete(Integer id);
    Optional<Category> getById(Integer id);
}
