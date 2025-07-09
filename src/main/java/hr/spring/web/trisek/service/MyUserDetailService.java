package hr.spring.web.trisek.service;

import hr.spring.web.trisek.repository.UserRepository;
import lombok.AllArgsConstructor;
import hr.spring.web.trisek.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(
                () -> new UsernameNotFoundException("User not found: " + username)
        );

        String roleName = user.getRole().getName();

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(roleName)
                .build();
    }

}
