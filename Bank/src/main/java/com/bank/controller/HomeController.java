package com.bank.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bank.domain.Account;
import com.bank.domain.AccountType;
import com.bank.domain.User;
import com.bank.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/signup")
    public String signup(Model model) {

        model.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, Model model) {

        User tempUser = userService.findByUsername(user.getUsername());

        if (tempUser != null) {
            model.addAttribute("usernameExists", true);
            return "signup";
        } else {
            tempUser = userService.findByEmail(user.getEmail());

            if (tempUser != null) {
                model.addAttribute("emailExists", true);
                return "signup";
            }
        }

        userService.createUser(user);

        model.addAttribute("userAdded", true);

        return "redirect:/";

    }

    @GetMapping("/userFront")
    public String userFront(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        List<Account> userAccounts = user.getAccounts();
        for (Account account : userAccounts) {
            if (account.getType().equals(AccountType.CURRENT))
                model.addAttribute("currentAccount", account);
            else
                model.addAttribute("savingsAccount", account);
        }

        return "userFront";
    }
}
