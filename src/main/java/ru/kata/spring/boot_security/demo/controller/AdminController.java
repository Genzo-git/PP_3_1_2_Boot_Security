package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));
        model.addAttribute("userRoles", userDetails.getAuthorities());
        List<User> allUsrs = userService.getAllUsers();
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("allUsers", userService.findAllWithRoles());
        model.addAttribute("newUser", new User());
        return "all-users";
    }

    @GetMapping("/addNewUser")
    public String addNewUser(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "user-info";
    }

    @PostMapping("saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/updateInfo")
    public String updateUser(@RequestParam("userId") int id, Model model) {

        User user = userService.getUser(id);
        model.addAttribute("user", user);

        return "user-info";
    }

    @PostMapping("/updateInfo")
    public String updateUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int id) {

        userService.deleteUser(id);

        return "redirect:/admin";
    }

}
