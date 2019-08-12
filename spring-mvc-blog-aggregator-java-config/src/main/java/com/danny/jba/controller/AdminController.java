package com.danny.jba.controller;

import com.danny.jba.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@AllArgsConstructor

@Controller
public class AdminController {

    private UserService userService;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String userDetail(@PathVariable int id, Model model) {
        model.addAttribute("user", userService.findByIdWithBlogs(id));
        return "user-detail";
    }

    @GetMapping("/users/remove/{id}")
    public String removeUser(@PathVariable int id) {
        userService.deleteById(id);
        return "redirect:/users.html";
    }
    
    @ExceptionHandler(value = Exception.class)
    public String handleException(HttpServletRequest request, Exception ex) {
        log.error(String.format("Request: %s - threw an exception: %s", request.getRequestURI(), ex));
        return "error-specific";
    }
}
