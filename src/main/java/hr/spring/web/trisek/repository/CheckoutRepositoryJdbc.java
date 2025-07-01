//package hr.spring.web.trisek.repository;
//
//import hr.spring.web.trisek.model.Checkout;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.*;
//
//@Repository
//public class CheckoutRepositoryJdbc implements CheckoutRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert simpleJdbcInsert;
//
//    public CheckoutRepositoryJdbc(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("CHECKOUT")
//                .usingGeneratedKeyColumns("ID");
//    }
//
//    @Override
//    public List<Checkout> getAll() {
//        return jdbcTemplate.query("SELECT * FROM CHECKOUT", new CheckoutRowMapper());
//    }
//
//    @Override
//    public Checkout save(Checkout checkout) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("CUSTOMER_NAME", checkout.getCustomerName());
//        params.put("EMAIL", checkout.getEmail());
//        params.put("ADDRESS", checkout.getAddress());
//        params.put("AMOUNT", checkout.getAmount());
//        params.put("PAYMENT_METHOD", checkout.getPaymentMethod());
//        params.put("PAYMENT_STATUS", checkout.getPaymentStatus());
//        Number generatedPrimaryKey = simpleJdbcInsert.executeAndReturnKey(params);
//        checkout.setId(generatedPrimaryKey.intValue());
//        return checkout;
//    }
//
//    @Override
//    public boolean delete(Integer id) {
//        int rowsAffected = jdbcTemplate.update("DELETE FROM CHECKOUT WHERE ID = ?", id);
//        return rowsAffected > 0;
//    }
//
//    @Override
//    public Optional<Checkout> getById(Integer id) {
//        Checkout checkout = jdbcTemplate.queryForObject("SELECT * FROM CHECKOUT WHERE ID = ?", new CheckoutRowMapper(), id);
//        return Optional.ofNullable(checkout);
//    }
//
//    private static class CheckoutRowMapper implements RowMapper<Checkout> {
//        @Override
//        public Checkout mapRow(ResultSet rs, int rowNum) throws SQLException {
//            Checkout checkout = new Checkout();
//            checkout.setId(rs.getInt("ID"));
//            checkout.setCustomerName(rs.getString("CUSTOMER_NAME"));
//            checkout.setEmail(rs.getString("EMAIL"));
//            checkout.setAddress(rs.getString("ADDRESS"));
//            checkout.setAmount(rs.getDouble("AMOUNT"));
//            checkout.setPaymentMethod(rs.getString("PAYMENT_METHOD"));
//            checkout.setPaymentStatus(rs.getString("PAYMENT_STATUS"));
//            return checkout;
//        }
//    }
//}
