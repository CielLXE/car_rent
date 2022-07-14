package com.example.carrent.controller;

import com.example.carrent.bean.User;
import com.example.carrent.services.UserService;
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
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return user;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User did not exist");
        }
    }

    @PostMapping(value = "/login")
    public User login(@RequestBody User user) {
        User loginUser = userService.login(user);
        if (loginUser != null) {
            return loginUser;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and password did not match");
        }
    }

    @PostMapping(value = "")
    public int addUser(@RequestBody User user) {
        if (userService.addUser(user)) {
            return user.getId();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name exists");
        }
    }

    @PutMapping(value = "/{id}")
    public void putUser(@RequestBody User user) {
        if (userService.updateUser(user)) {
            throw new ResponseStatusException(HttpStatus.OK, "User updated");
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User did not exist or repeated user name");
        }
    }

}
