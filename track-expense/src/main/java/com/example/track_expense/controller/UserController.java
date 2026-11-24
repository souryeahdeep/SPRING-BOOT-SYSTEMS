package com.example.track_expense.controller;

import com.example.track_expense.dto.TransactionResponse;
import com.example.track_expense.dto.TransactionResponseWithCount;
import com.example.track_expense.dto.UserDto;
import com.example.track_expense.dto.UserResponse;
import com.example.track_expense.services.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    /// USER REGISTRATION
    @PostMapping
    public UserResponse createUser(@RequestBody UserDto user) {
        return userService.createUser(user);
    }
    /// GET ALL USER
    @GetMapping
    public List<UserResponse> findAll(){
        return userService.findAllUsers();
    }

}
