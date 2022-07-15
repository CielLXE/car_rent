package com.example.carrent.controller;

import com.example.carrent.bean.Car;
import com.example.carrent.bean.Result;
import com.example.carrent.bean.User;
import com.example.carrent.services.UserService;
import com.example.carrent.utils.ResultGenerator;
import com.example.carrent.utils.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
@Api
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "")
    @ApiOperation(value = "get all users in the database")
    public Result getAllUsers() {
        List<User> users =  userService.getAllUsers();
        return ResultGenerator.genSuccessResult(users);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "get user by primary key - id")
    public Result getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResultGenerator.genSuccessResult(user);
        } else {
            return ResultGenerator.genFailResult("user not exist");
        }
    }

    @PostMapping(value = "/login")
    @ApiOperation(value = "login and verify if user legal : user exists and password matches")
    public Result login(@RequestBody User user) {
        try {
            User loginUser = userService.login(user);
            return ResultGenerator.genSuccessResult(loginUser);
        } catch (ServiceException e) {
            return ResultGenerator.genFailResult(e.getMessage());
        }
    }

    @PostMapping(value = "")
    @ApiOperation(value = "add a new user with unique name")
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
    @ApiOperation(value = "update a user's information by primary key - id")
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
