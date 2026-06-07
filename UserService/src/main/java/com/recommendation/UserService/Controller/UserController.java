package com.recommendation.UserService.Controller;


import org.springframework.web.bind.annotation.*;

import com.recommendation.UserService.Service.UserService;
import com.recommendation.UserService.dto.LoginRequest;
import com.recommendation.UserService.dto.UserRequest;
import com.recommendation.UserService.dto.UserResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // 🔹 Constructor Injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
}