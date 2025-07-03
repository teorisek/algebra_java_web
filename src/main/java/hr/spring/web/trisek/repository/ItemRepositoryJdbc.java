package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.Category;
import hr.spring.web.trisek.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ItemRepositoryJdbc implements ItemRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final CategoryRepository categoryRepository;

    public ItemRepositoryJdbc(JdbcTemplate jdbcTemplate, CategoryRepository categoryRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.categoryRepository = categoryRepository;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("item")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Item> getAll() {
        return jdbcTemplate.query("SELECT * FROM ITEM", new ItemRowMapper());
    }

    @Override
    public Item save(Item item) {
        if (item.getId() == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("name", item.getName());
            params.put("description", item.getDescription());
            params.put("quantity", item.getQuantity());
            params.put("price", item.getPrice());
            params.put("category_id", item.getCategory() != null ? item.getCategory().getId() : null);

            Number generatedPrimaryKey = simpleJdbcInsert.executeAndReturnKey(params);
            item.setId(generatedPrimaryKey.intValue());
        } else {
            jdbcTemplate.update(
                    "UPDATE item SET name = ?, description = ?, quantity = ?, price = ?, category_id = ? WHERE id = ?",
                    item.getName(),
                    item.getDescription(),
                    item.getQuantity(),
                    item.getPrice(),
                    item.getCategory() != null ? item.getCategory().getId() : null,
                    item.getId()
            );
        }
        return item;
    }


    @Override
    public boolean delete(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM ITEM WHERE ID = ?", id);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Item> getById(Integer id) {
        Item item = jdbcTemplate.queryForObject("SELECT * FROM ITEM WHERE ID = ?", new ItemRowMapper(), id);
        return Optional.ofNullable(item);
    }



    private class ItemRowMapper implements RowMapper<Item> {

        @Override
        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setDescription(rs.getString("description"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPrice(rs.getBigDecimal("price"));

            int categoryId = rs.getInt("category_id");
            Category category = categoryRepository.getById(categoryId).orElse(null);
            item.setCategory(category);

            return item;
        }
    }
}









