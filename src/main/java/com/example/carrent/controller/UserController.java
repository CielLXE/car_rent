package com.example.carrent.controller;

import com.example.carrent.bean.Car;
import com.example.carrent.bean.Result;
import com.example.carrent.bean.User;
import com.example.carrent.services.UserService;
import com.example.carrent.utils.ResultGenerator;
import com.example.carrent.utils.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    public Result getAllUsers() {
        List<User> users =  userService.getAllUsers();
        return ResultGenerator.genSuccessResult(users);
    }

    @GetMapping(value = "/{id}")
    public Result getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResultGenerator.genSuccessResult(user);
        } else {
            return ResultGenerator.genFailResult("user not exist");
        }
    }

    @PostMapping(value = "/login")
    public Result login(@RequestBody User user) {
        try {
            User loginUser = userService.login(user);
            return ResultGenerator.genSuccessResult(loginUser);
        } catch (ServiceException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @PostMapping(value = "")
    public Result addUser(@RequestBody User user) {
        try {
            if (userService.addUser(user)) {
                return ResultGenerator.genSuccessResult(user.getId());
            } else {
                return ResultGenerator.genFailResult("add user fail");
            }
        } catch (ServiceException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @PutMapping(value = "/{id}")
    public Result putUser(@RequestBody User user) {
        try {
            if (userService.updateUser(user)) {
                return ResultGenerator.genSuccessResult();
            } else {
                return ResultGenerator.genFailResult("update user fail");
            }
        } catch (ServiceException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

}
