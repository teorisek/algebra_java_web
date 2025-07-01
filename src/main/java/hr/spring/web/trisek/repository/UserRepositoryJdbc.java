package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.Role;
import hr.spring.web.trisek.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class UserRepositoryJdbc implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public UserRepositoryJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("ID");
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM USERS", new UserRowMapper());
    }

    @Override
    public User save(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("USERNAME", user.getUsername());
        params.put("PASSWORD", user.getPassword());
        params.put("ENABLED", user.getEnabled());
        params.put("ROLE_ID", user.getRole().getId());
        Number generatedPrimaryKey = simpleJdbcInsert.executeAndReturnKey(params);
        user.setId(generatedPrimaryKey.intValue());
        return user;
    }

    @Override
    public boolean delete(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM USERS WHERE ID = ?", id);
        return rowsAffected > 0;
    }

    @Override
    public Optional<User> getById(Integer id) {
        User user = jdbcTemplate.queryForObject("SELECT * FROM USERS WHERE ID = ?", new UserRowMapper(), id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT u.*, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), username);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));

            Role role = new Role();
            role.setId(rs.getInt("role_id"));
            role.setName(rs.getString("role_name"));
            user.setRole(role);

            return user;
        }
    }
}
