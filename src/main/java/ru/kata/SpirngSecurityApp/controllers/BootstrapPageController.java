package ru.kata.SpirngSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import ru.kata.SpirngSecurityApp.DTO.UserDTO;
import ru.kata.SpirngSecurityApp.models.Role;
import ru.kata.SpirngSecurityApp.models.User;
import ru.kata.SpirngSecurityApp.services.RoleServiceInt;
import ru.kata.SpirngSecurityApp.services.UserServiceInt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            Optional<User> userOptional = userServiceInt.findById(id);
            if (userOptional.isPresent()) {
                userServiceInt.delete(userOptional.get());
                return ResponseEntity.ok("{\"success\": true}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/main_page")
    public String showBootstrapPage(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleServiceInt.findAll());

        return "/main_page";
    }

    @GetMapping("/current_user")
    @ResponseBody
    public UserDTO showCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAge(user.getAge());
        userDTO.setEmail(user.getEmail());

        List<String> roles = user.getUserRolesList().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
        userDTO.setRoles(roles);

        return userDTO;
    }

    @GetMapping("/list")
    @ResponseBody
    public List<UserDTO> getAllUsers() {
        List<User> userList = userServiceInt.findAll();
        List<UserDTO> userDTOList = userList.stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getAge(),
                        user.getEmail(),
                        user.getUserRolesList().stream()
                                .map(Role::getName)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
        return userDTOList;
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
