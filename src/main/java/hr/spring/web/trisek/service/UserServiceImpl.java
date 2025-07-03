package hr.spring.web.trisek.service;

import hr.spring.web.trisek.model.Role;
import hr.spring.web.trisek.model.User;
import hr.spring.web.trisek.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User save(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            if (!user.getPassword().startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        } else if (user.getId() != null) {
            user.setPassword(userRepository.getById(user.getId()).map(User::getPassword).orElse(null));
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getById(long id) {
        return userRepository.getById((int) id);
    }

    @Override
    public boolean delete(long id) {
        return userRepository.delete((int) id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Role> getAllRoles() {
        return userRepository.getAllRoles();
    }

}
