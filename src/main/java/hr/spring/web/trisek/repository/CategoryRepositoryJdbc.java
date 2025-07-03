package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class CategoryRepositoryJdbc implements CategoryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CategoryRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("category")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Category> getAll() {
        return jdbcTemplate.query("SELECT * FROM category", new CategoryRowMapper());
    }

    @Override
    public Category save(Category category) {
        if (category.getId() == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("name", category.getName());
            params.put("description", category.getDescription());
            Number generatedPrimaryKey = simpleJdbcInsert.executeAndReturnKey(params);
            category.setId(generatedPrimaryKey.intValue());
        } else {
            jdbcTemplate.update(
                    "UPDATE category SET name = ?, description = ? WHERE id = ?",
                    category.getName(),
                    category.getDescription(),
                    category.getId()
            );
        }
        return category;
    }

    @Override
    public boolean delete(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM category WHERE id = ?", id);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Category> getById(Integer id) {
        Category category = jdbcTemplate.queryForObject("SELECT * FROM category WHERE id = ?", new CategoryRowMapper(), id);
        return Optional.ofNullable(category);
    }

    private static class CategoryRowMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setDescription(rs.getString("description"));
            return category;
        }

    }
}
