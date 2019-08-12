package com.danny.flightreservation.controller;

import com.danny.flightreservation.entity.User;
import com.danny.flightreservation.repository.UserRepository;
import com.danny.flightreservation.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/showReg")
    public String showRegistrationPage() {
        return "login/registerUser";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "login/login";
    }

    @GetMapping("/showLogin")
    public String login() {
        return "login/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        boolean loginResponse = securityService.login(email, password);

        if (loginResponse)
            return "findFlights";
        else
            model.addAttribute("msg", "Invalid username or password");

        return "login/login";
    }
}
