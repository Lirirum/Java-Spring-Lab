package com.karacheban.demo.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final Map<Long, String> users = new HashMap<>();

    @PostMapping("/register")
    public String registerUser(@RequestParam String username) {
        long id = users.size() + 1;
        users.put(id, username);
        return "User registered with ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        users.remove(id);
        return "User deleted";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username) {
        return "Login successful for user: " + username;
    }


}