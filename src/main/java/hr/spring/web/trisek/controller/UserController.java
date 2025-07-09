package hr.spring.web.trisek.controller;

import hr.spring.web.trisek.model.User;
import hr.spring.web.trisek.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "user";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Long id, Model model) {
        Optional<User> userOpt = userService.getById(id);
        if (userOpt.isEmpty()) return "redirect:/admin/users";
        model.addAttribute("user", userOpt.get());
        model.addAttribute("roles", userService.getAllRoles());
        return "user-edit";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, @ModelAttribute User user) {
        user.setId(id.intValue());
        userService.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", userService.getAllRoles());
        return "user-create";
    }


    @PostMapping("/create")
    public String createUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }
}
