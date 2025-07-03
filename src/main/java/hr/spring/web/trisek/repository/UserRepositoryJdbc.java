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
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT u.*, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User save(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        params.put("enabled", user.getEnabled());
        params.put("role_id", user.getRole().getId());

        if (user.getId() == null) {
            Number generatedPrimaryKey = simpleJdbcInsert.executeAndReturnKey(params);
            user.setId(generatedPrimaryKey.intValue());
        } else {
            String sql = "UPDATE users SET username=?, password=?, enabled=?, role_id=? WHERE id=?";
            jdbcTemplate.update(sql,
                    user.getUsername(),
                    user.getPassword(),
                    user.getEnabled(),
                    user.getRole().getId(),
                    user.getId());
        }
        return user;
    }

    @Override
    public boolean delete(Integer id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);
        return rowsAffected > 0;
    }

    @Override
    public Optional<User> getById(Integer id) {
        String sql = "SELECT u.*, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), id);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT u.*, r.name AS role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), username);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<Role> getAllRoles() {
        String sql = "SELECT * FROM roles";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setName(rs.getString("name"));
            return role;
        });
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
