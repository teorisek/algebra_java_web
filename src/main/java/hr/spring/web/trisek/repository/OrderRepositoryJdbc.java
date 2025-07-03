package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.Order;
import hr.spring.web.trisek.model.OrderItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.util.*;

@Repository
public class OrderRepositoryJdbc implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public OrderRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("orders")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Order> getAll() {
        return jdbcTemplate.query("SELECT * FROM orders", new OrderRowMapper());
    }

    @Override
    public Order save(Order order) {
        Map<String, Object> params = new HashMap<>();
        params.put("customer_name", order.getCustomerName());
        params.put("email", order.getEmail());
        params.put("address", order.getAddress());
        params.put("amount", order.getAmount());
        params.put("payment_method", order.getPaymentMethod());
        params.put("payment_status", order.getPaymentStatus());
        params.put("user_id", order.getUserId());
        params.put("order_date", Timestamp.valueOf(order.getOrderDate().toLocalDateTime()));
        Number generatedPrimaryKey = simpleJdbcInsert.executeAndReturnKey(params);
        order.setId(generatedPrimaryKey.intValue());

        if (order.getItems() != null) {
            for (OrderItem item : order.getItems()) {
                jdbcTemplate.update(
                        "INSERT INTO order_item (order_id, item_id, quantity, price, item_name, item_description) VALUES (?, ?, ?, ?, ?, ?)",
                        order.getId(),
                        item.getItemId(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getItemName(),
                        item.getItemDescription()
                );
            }
        }

        return order;
    }

    @Override
    public boolean delete(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM orders WHERE id = ?", id);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Order> getById(Integer id) {
        Order order = jdbcTemplate.queryForObject("SELECT * FROM orders WHERE id = ?", new OrderRowMapper(), id);
        return Optional.ofNullable(order);
    }

    private static class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setCustomerName(rs.getString("customer_name"));
            order.setEmail(rs.getString("email"));
            order.setAddress(rs.getString("address"));
            order.setAmount(rs.getBigDecimal("amount"));
            order.setPaymentMethod(rs.getString("payment_method"));
            order.setPaymentStatus(rs.getString("payment_status"));
            order.setUserId(rs.getInt("user_id"));
            order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime().atOffset(ZoneOffset.UTC));
            return order;
        }
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        return jdbcTemplate.query(
                "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC",
                new OrderRowMapper(),
                userId
        );
    }

    @Override
    public List<OrderItem> findItemsByOrderId(Integer orderId) {
        return jdbcTemplate.query(
                "SELECT * FROM order_item WHERE order_id = ?",
                (rs, rowNum) -> {
                    OrderItem item = new OrderItem();
                    item.setId(rs.getInt("id"));
                    item.setItemId(rs.getObject("item_id") != null ? rs.getInt("item_id") : null);
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getBigDecimal("price"));
                    item.setItemName(rs.getString("item_name"));
                    item.setItemDescription(rs.getString("item_description"));
                    return item;
                },
                orderId
        );
    }
}
