package hr.spring.web.trisek.controller;

import hr.spring.web.trisek.model.Login;
import hr.spring.web.trisek.repository.LoginRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminLoginController {

    private final LoginRepository loginRepository;

    public AdminLoginController(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @GetMapping("/admin/logins")
    public String showLogins(Model model) {
        List<Login> logins = loginRepository.findAll();
        model.addAttribute("logins", logins);
        return "logins";
    }
}
