package hr.spring.web.trisek.service;

import hr.spring.web.trisek.model.Role;
import hr.spring.web.trisek.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    User save(User user);
    Optional<User> getById(long id);
    boolean delete(long id);
    Optional<User> findByUsername(String username);
    List<Role> getAllRoles();
}
