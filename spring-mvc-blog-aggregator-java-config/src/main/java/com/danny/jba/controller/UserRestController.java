package com.danny.jba.controller;

import com.danny.jba.entity.User;
import com.danny.jba.exception.UserNotFoundException;
import com.danny.jba.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @GetMapping()
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {

        User user = userService.findById(id);

        if (user == null)
            throw new UserNotFoundException("User id not found - " + id);

        return user;
    }

    // Add new user
    @PostMapping
    public User addUser(@RequestBody User user) {

        // also just in case the pass an id in JSON ... set id to 0
        // this is force a save of new item ... instead of update
        user.setId(0);

        userService.save(user);

        return user;
    }

    // Update existing user
    @PutMapping()
    public User updateUser(@RequestBody User user) {

        userService.save(user);

        return user;
    }

    // Delete user
    @DeleteMapping("/{id}")
    public String DeleteUser(@PathVariable int id) {

        User user = userService.findById(id);

        if (user == null)
            throw new UserNotFoundException("User id not found - " + id);

        userService.deleteById(id);

        return "Deleted user id - " + id;
    }
}