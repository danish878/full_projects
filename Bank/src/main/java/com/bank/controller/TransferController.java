package com.bank.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bank.domain.Recipient;
import com.bank.domain.User;
import com.bank.service.TransactionService;
import com.bank.service.UserService;

@Controller
@RequestMapping("/transfer")
public class TransferController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/betweenAccounts")
    public String betweenAccounts(Model model) {

        model.addAttribute("transferFrom", "");
        model.addAttribute("transferTo", "");
        model.addAttribute("amount", "");

        return "betweenAccounts";
    }

    @PostMapping("/betweenAccounts")
    public String betweenAccounts(
            @ModelAttribute("transferFrom") String transferFrom,
            @ModelAttribute("transferTo") String transferTo,
            @ModelAttribute("amount") String amount,
            Principal principal,
            Model model) {

        User user = userService.findByUsername(principal.getName());

        try {
            transactionService.betweenAccountsTransfer(transferFrom, transferTo, new BigDecimal(amount), user);
        } catch (IllegalAccessException e) {
            model.addAttribute("lowBalance", true);
            return "betweenAccounts";
        } catch (Exception e) {
            model.addAttribute("invalidTransfer", true);
            return "betweenAccounts";
        }

        return "redirect:/userFront";
    }

    @GetMapping("/recipient")
    public String recipient(Model model, Principal principal) {

        List<Recipient> recipientList = transactionService.findRecipientList(principal.getName());

        model.addAttribute("recipientList", recipientList);
        model.addAttribute("recipient", new Recipient());

        return "recipient";
    }

    @PostMapping("/recipient/save")
    public String addRecipient(@ModelAttribute("recipient") Recipient recipient, Principal principal, Model model) {

        try {
            transactionService.saveRecipient(recipient, principal.getName());
        } catch (IllegalAccessException e) {
            model.addAttribute("recipientNotExists", true);
            return "recipient";
        }

        return "redirect:/transfer/recipient";
    }

    @GetMapping("/recipient/edit")
    public String editRecipient(@RequestParam("recipientId") String recipientId, Model model, Principal principal) {

        Recipient recipient = transactionService.findRecipientById(Long.parseLong(recipientId));
        List<Recipient> recipientList = transactionService.findRecipientList(principal.getName());

        model.addAttribute("recipientList", recipientList);
        model.addAttribute("recipient", recipient);

        return "recipient";
    }

    @GetMapping("/recipient/delete")
    public String deleteRecipient(@RequestParam("recipientId") String recipientId, Model model) {

        transactionService.deleteRecipientById(Long.parseLong(recipientId));

        model.addAttribute("recipientDelete", true);

        return "redirect:/transfer/recipient";
    }

    @GetMapping("/localTransfer")
    public String localTransfer(Model model, Principal principal) {

        List<Recipient> recipientList = transactionService.findRecipientList(principal.getName());

        model.addAttribute("recipientList", recipientList);
        model.addAttribute("accountType", "");

        return "localTransfer";
    }

    @PostMapping("/localTransfer")
    public String localTransfer(
            @ModelAttribute("recipientId") String recipientId,
            @ModelAttribute("accountType") String accountType,
            @ModelAttribute("amount") String amount,
            Model model,
            Principal principal) {

        User user = userService.findByUsername(principal.getName());

        try {
            transactionService.localTransfer(user, Long.parseLong(recipientId), accountType, new BigDecimal(amount));
        } catch (IllegalAccessException e) {
            model.addAttribute("lowBalance", true);
            return "localTransfer";
        }


        return "redirect:/userFront";
    }

}
