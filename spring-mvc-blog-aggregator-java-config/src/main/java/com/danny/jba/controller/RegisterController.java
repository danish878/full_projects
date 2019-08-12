package com.danny.jba.controller;

import com.danny.jba.entity.User;
import com.danny.jba.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@AllArgsConstructor

@Controller
@RequestMapping("/register")
public class RegisterController {

    private UserService userService;

    @ModelAttribute("user")
    public User constructUser() {
        return new User();
    }

    @GetMapping
    public String showRegistrer() {
        return "user-register";
    }

    @PostMapping
    public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors())
            return showRegistrer();

        userService.save(user);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/register.html";
    }

    @GetMapping("/available")
    @ResponseBody
    public String available(@RequestParam String username) {
        boolean available = userService.findByUsername(username) == null;
        return Boolean.toString(available);
    }
}
