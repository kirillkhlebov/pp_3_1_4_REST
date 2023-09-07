package ru.kata.SpirngSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.kata.SpirngSecurityApp.models.User;
import ru.kata.SpirngSecurityApp.services.RoleServiceInt;
import ru.kata.SpirngSecurityApp.services.UserServiceInt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BootstrapPageController {

    private final UserServiceInt userServiceInt;
    private final RoleServiceInt roleServiceInt;

    @Autowired
    public BootstrapPageController(UserServiceInt userServiceInt, RoleServiceInt roleServiceInt) {
        this.userServiceInt = userServiceInt;
        this.roleServiceInt = roleServiceInt;
    }


    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public Map<String, String> updateUser(@PathVariable Long id, @ModelAttribute("user") User updatedUser) {
        Map<String, String> response = new HashMap<>();

        if (userServiceInt.isEmailCanNotBeChanged(updatedUser, id)) {
            response.put("error", "User with this email already exists");
        } else {
            userServiceInt.save(updatedUser);
        }

        return response;
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userServiceInt.findById(id);
        userServiceInt.delete(userOptional.get());
        return "redirect:/main_page";
    }

    @GetMapping("/main_page")
    public String showBootstrapPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", user);

        List<User> listOfUsers = userServiceInt.findAll();
        model.addAttribute("users", listOfUsers);

        model.addAttribute("user", new User());
        model.addAttribute("roles", roleServiceInt.findAll());

        return "/main_page";
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Map<String, String>> createUser(@ModelAttribute User user) {
        Map<String, String> response = new HashMap<>();
        if (userServiceInt.isExist(user.getEmail())) {
            response.put("error", "User with this email already exists");
            return ResponseEntity.badRequest().body(response);
        }
        userServiceInt.saveAndFlush(user);
        return ResponseEntity.ok(response);
    }
}
