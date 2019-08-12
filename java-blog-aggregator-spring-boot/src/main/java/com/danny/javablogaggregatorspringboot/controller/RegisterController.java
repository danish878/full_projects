package com.danny.javablogaggregatorspringboot.controller;

import com.danny.javablogaggregatorspringboot.entity.User;
import com.danny.javablogaggregatorspringboot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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

    @RequestMapping
    public String showRegister(Model model) {
        model.addAttribute("current", "register");
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doRegister(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("register");
        }
        userService.save(user);
        RedirectView redirectView = new RedirectView("/register?success=true");
        redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
    }

    @RequestMapping("/available")
    @ResponseBody
    public String available(@RequestParam String username) {
        boolean available = userService.findOne(username) == null;
        return Boolean.toString(available);
    }

}
