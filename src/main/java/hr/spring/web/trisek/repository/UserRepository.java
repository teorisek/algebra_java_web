package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> getAll();
    User save(User user);
    boolean delete(Integer id);
    Optional<User> getById(Integer id);
    Optional<User> findByUsername(String username);
}
