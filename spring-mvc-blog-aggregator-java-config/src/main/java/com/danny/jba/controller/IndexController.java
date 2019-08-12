package com.danny.jba.controller;

import com.danny.jba.service.EmailService;
import com.danny.jba.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor

@Controller
public class IndexController {

    private final ItemService itemService;
    private final EmailService emailService;

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("items", itemService.getItems());
        return "index"; // path comes from 'general.xml' definition 'index'
    }

    @GetMapping("/email")
    public String testEmail(Model model) {
        String to = "mohamad.aslam.0864@gmail.com";
        String subject = "Test Email from Spring MVC Java Config";
        String text = "If you are reading this then Hurray Success!!!";
//        String pathToAttachment = "C:/Users/Danish/Desktop/css-animation/images/sky.jpg";

        emailService.sendSimpleMessage(to, subject, text);
//        emailService.sendMessageWithAttachment(to, subject, text, pathToAttachment);

        model.addAttribute("email", "Email Sent Successfully");
        return "email";
    }
}
