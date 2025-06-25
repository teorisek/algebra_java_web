package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class CategoryRepositoryJdbcTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void givenCategory_whenSave_thenWeCanLoadFromDatabase() {
        Category category = new Category();
        category.setName("test name");
        category.setDescription("test description");
        categoryRepository.save(category);

        List<Category> allCategories = categoryRepository.getAll();
        assertEquals(1, allCategories.size());
        assertEquals("test name", allCategories.getFirst().getName());
        assertEquals("test description", allCategories.getFirst().getDescription());
    }
}