package hr.spring.web.trisek.service;

import hr.spring.web.trisek.model.Login;
import hr.spring.web.trisek.repository.LoginRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Service
public class LoginAuditService {
    private final LoginRepository loginRepository;

    public LoginAuditService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public void logLogin(String username, String ip) {
        Login login = new Login(username, LocalDateTime.now(), ip);
        loginRepository.save(login);
    }
}
