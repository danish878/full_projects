package com.bank.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bank.domain.Account;
import com.bank.domain.AccountType;
import com.bank.domain.User;
import com.bank.service.UserService;
import com.bank.service.impl.AccountServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        Account currentAccount = AccountServiceImpl.getSpecificAccount(user.getAccounts(), AccountType.CURRENT);
        Account savingsAccount = AccountServiceImpl.getSpecificAccount(user.getAccounts(), AccountType.SAVINGS);

        model.addAttribute("user", user);
        model.addAttribute("currentAccountNumber", currentAccount.getNumber());
        model.addAttribute("savingsAccountNumber", savingsAccount.getNumber());

        return "profile";
    }

    @PostMapping("/profile")
    public String profile(@ModelAttribute("user") User newUser, Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName());
        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        user.setUsername(newUser.getUsername());

        userService.save(user);

        return "redirect:/user/profile";
    }


}
