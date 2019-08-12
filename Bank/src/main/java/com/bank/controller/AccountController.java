package com.bank.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bank.domain.Account;
import com.bank.domain.AccountType;
import com.bank.domain.Transaction;
import com.bank.domain.User;
import com.bank.service.AccountService;
import com.bank.service.TransactionService;
import com.bank.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/currentAccount")
    public String currentAccount(Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName());
        List<Account> accounts = user.getAccounts();
        for (Account account : accounts) {
            if (account.getType().equals(AccountType.CURRENT))
                model.addAttribute("currentAccount", account);
        }

        List<Transaction> currentTransactionList = transactionService.findTransactionList(user.getUsername(), AccountType.CURRENT);
        model.addAttribute("currentTransactionList", currentTransactionList);

        return "currentAccount";
    }

    @GetMapping("/savingsAccount")
    public String savingsAccount(Principal principal, Model model) {

        User user = userService.findByUsername(principal.getName());
        List<Account> accounts = user.getAccounts();
        for (Account account : accounts) {
            if (account.getType().equals(AccountType.SAVINGS))
                model.addAttribute("savingsAccount", account);
        }

        List<Transaction> savingsTransactionList = transactionService.findTransactionList(user.getUsername(), AccountType.SAVINGS);
        model.addAttribute("savingsTransactionList", savingsTransactionList);

        return "savingsAccount";
    }

    @GetMapping("/deposit")
    public String deposit(Model model) {

        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "deposit";
    }

    @PostMapping("/deposit")
    public String deposit(@ModelAttribute("amount") String amount,
                          @ModelAttribute("accountType") String accountType,
                          Principal principal) {

        AccountType type = (accountType.equalsIgnoreCase(AccountType.CURRENT.toString())) ?
                AccountType.CURRENT : AccountType.SAVINGS;

        accountService.deposit(type, Double.parseDouble(amount), principal);

        return "redirect:/userFront";
    }

    @GetMapping("/withdraw")
    public String withdraw(Model model) {

        model.addAttribute("accountType", "");
        model.addAttribute("amount", "");

        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(@ModelAttribute("amount") String amount,
                           @ModelAttribute("accountType") String accountType,
                           Principal principal, Model model) {

        AccountType type = (accountType.equalsIgnoreCase(AccountType.CURRENT.toString())) ?
                AccountType.CURRENT : AccountType.SAVINGS;

        try {
            accountService.withdraw(type, Double.parseDouble(amount), principal);
        } catch (Exception e) {
            model.addAttribute("lowBalance", true);

            return "withdraw";
        }

        return "redirect:/userFront";
    }
}
