package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    ////////////////////!!!!!!!!!
    @GetMapping("") // Работает но не работает update add
    public String showAllUsers(Model model) {
        List<User> allUsrs = userService.getAllUsers();
        model.addAttribute("allUsrs", allUsrs);
        //model.addAttribute("allUsrs", userService.getAllUsers());
        return "all-users";
    }

    @GetMapping("/addNewUser")
    // запускается admin/addNewUser (показывает по факту user-info пустую)  но при добавлении пользователя 8080/saveUser и 404
    public String addNewUser(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "user-info";
    }

    @PostMapping("saveUser") // ничего не происходит вообще
    public String saveUser(@ModelAttribute("user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);

        return "redirect:/admin";
    }

    @GetMapping("/updateInfo") // admin/updateInfo открывается но ошибка 400
    public String updateUser(@RequestParam("usrId") int id, Model model) {

        User user = userService.getUser(id);
        model.addAttribute("user", user);

        //model.addAttribute("user", userService.getUser(id));

        return "user-info";
    }

    //////
    @PostMapping("/updateInfo")
    public String updateUser(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
    /////

    @GetMapping("/deleteUser") // admin/deleteUser открывается но ошибка 400
    public String deleteUser(@RequestParam("usrId") int id) {

        userService.deleteUser(id);

        return "redirect:/admin";
    }

}
