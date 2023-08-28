//package ru.kata.SpirngSecurityApp.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.kata.SpirngSecurityApp.models.User;
//import ru.kata.SpirngSecurityApp.repo.RoleRepository;
//import ru.kata.SpirngSecurityApp.repo.UserRepository;
//import ru.kata.SpirngSecurityApp.security.UserDetailsImpl;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//
//    @Autowired
//    public AdminController(UserRepository userRepository, RoleRepository roleRepository) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//    }
//
//    @GetMapping("/admin")
//    public String showUsers(Model model) {
//        List<User> listOfUsers = userRepository.findAll();
//        model.addAttribute("users", listOfUsers);
//        return "admin/admin";
//    }
//
////    @GetMapping("/new")
////    public String showNewUserForm(Model model) {
////        model.addAttribute("user", new User());
////        model.addAttribute("roles", roleRepository.findAll());
////        return "admin/new";
////    }
//
//
////    @PostMapping("/new")
////    public String  createUser(@ModelAttribute User user, Model model) {
////        Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
////        if (existingUser.isPresent()) {
////            model.addAttribute("error", "User with this email already exists");
////            model.addAttribute("roles", roleRepository.findAll());
////            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
////            model.addAttribute("currentUser", userDetails.getUser());
////            return "/test";
////        }
////        userRepository.saveAndFlush(user);
////        return "/test";
////    }
//
//    @PostMapping("/new")
//    public ResponseEntity<Map<String, String>> createUser(@ModelAttribute User user) {
//        Map<String, String> response = new HashMap<>();
//        Optional<User> existingUser = userRepository.findUserByEmail(user.getEmail());
//        if (existingUser.isPresent()) {
//            response.put("error", "User with this email already exists");
//            return ResponseEntity.badRequest().body(response);
//        }
//        userRepository.saveAndFlush(user);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showEditUserForm(@PathVariable Long id, @ModelAttribute User user, Model model) {
//        Optional<User> userOptional = userRepository.findById(id);
//        model.addAttribute("user", userOptional.get());
//        model.addAttribute("roles", roleRepository.findAll());
//        return "admin/edit";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User updatedUser) {
//        Optional<User> existingUser = userRepository.findById(id);
//
//        if (existingUser.isPresent()) {
//            User userToUpdate = existingUser.get();
//            userToUpdate.setFirstName(updatedUser.getFirstName());
//            userToUpdate.setLastName(updatedUser.getLastName());
//            userToUpdate.setAge(updatedUser.getAge());
//            userToUpdate.setEmail(updatedUser.getEmail());
//            userToUpdate.setPassword(updatedUser.getPassword());
//            userToUpdate.setUserRolesList(updatedUser.getUserRolesList());
//            userRepository.save(userToUpdate);
//        }
//        return "redirect:/main_page";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteUser(@PathVariable Long id) {
//        Optional<User> userOptional = userRepository.findById(id);
//        userRepository.delete(userOptional.get());
//        return "redirect:/main_page";
//    }
//
//}
