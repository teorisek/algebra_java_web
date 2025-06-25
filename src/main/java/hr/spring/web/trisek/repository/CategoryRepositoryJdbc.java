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
                .withTableName("CATEGORY")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public List<Category> getAll() {
        return jdbcTemplate.query("SELECT * FROM CATEGORY", new CategoryRowMapper());
    }

    @Override
    public Category save(Category category) {
        Map<String, Object> params = new HashMap<>();
        params.put("NAME", category.getName());
        params.put("DESCRIPTION", category.getDescription());
        Number generatedPrimaryKey = simpleJdbcInsert.executeAndReturnKey(params);
        category.setId(generatedPrimaryKey.intValue());
        return category;
    }

    @Override
    public boolean delete(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM CATEGORY WHERE ID = ?", id);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Category> getById(Integer id) {
        Category category = jdbcTemplate.queryForObject("SELECT * FROM CATEGORY WHERE ID = ?", new CategoryRowMapper(), id);
        return Optional.ofNullable(category);
    }



    private static class CategoryRowMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category();
            category.setId(rs.getInt("ID"));
            category.setName(rs.getString("NAME"));

            category.setDescription(rs.getString("DESCRIPTION"));

            return category;
        }

    }
}









