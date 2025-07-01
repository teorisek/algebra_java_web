package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.Checkout;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class CheckoutRepositoryJdbc implements CheckoutRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CheckoutRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("checkout")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Checkout> getAll() {
        return jdbcTemplate.query("SELECT * FROM checkout", new CheckoutRowMapper());
    }

    @Override
    public Checkout save(Checkout checkout) {
        Map<String, Object> params = new HashMap<>();
        params.put("customer_name", checkout.getCustomerName());
        params.put("email", checkout.getEmail());
        params.put("address", checkout.getAddress());
        params.put("amount", checkout.getAmount());
        params.put("payment_method", checkout.getPaymentMethod());
        params.put("payment_status", checkout.getPaymentStatus());
        Number generatedPrimaryKey = simpleJdbcInsert.executeAndReturnKey(params);
        checkout.setId(generatedPrimaryKey.intValue());
        return checkout;
    }

    @Override
    public boolean delete(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM checkout WHERE id = ?", id);
        return rowsAffected > 0;
    }

    @Override
    public Optional<Checkout> getById(Integer id) {
        Checkout checkout = jdbcTemplate.queryForObject("SELECT * FROM checkout WHERE id = ?", new CheckoutRowMapper(), id);
        return Optional.ofNullable(checkout);
    }

    private static class CheckoutRowMapper implements RowMapper<Checkout> {
        @Override
        public Checkout mapRow(ResultSet rs, int rowNum) throws SQLException {
            Checkout checkout = new Checkout();
            checkout.setId(rs.getInt("id"));
            checkout.setCustomerName(rs.getString("customer_name"));
            checkout.setEmail(rs.getString("email"));
            checkout.setAddress(rs.getString("address"));
            checkout.setAmount(rs.getBigDecimal("amount"));
            checkout.setPaymentMethod(rs.getString("payment_method"));
            checkout.setPaymentStatus(rs.getString("payment_status"));
            return checkout;
        }
    }
}
