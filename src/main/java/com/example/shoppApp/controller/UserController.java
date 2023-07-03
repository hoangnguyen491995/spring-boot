package com.example.shoppApp.controller;

import com.example.shoppApp.entity.User;
import com.example.shoppApp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@CrossOrigin
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping("/admin/list")
    public List<User> getAllUser() {
        return userService.getAllUser();
    }

    @PostMapping("/admin/add")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/auth/register")
    public User register(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/auth/login")
    public User login(@RequestBody User user) {
        return userService.login(user);
    }

    @PutMapping("/admin/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        return userService.deleteUser(Long.parseLong(id));
    }

    @GetMapping("/admin/birthday")
    public List<User> getUserByBirthday() {
        return userService.getUserByBirthday();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

}
