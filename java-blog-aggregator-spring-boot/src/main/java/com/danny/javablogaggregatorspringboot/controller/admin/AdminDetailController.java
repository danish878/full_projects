package com.danny.javablogaggregatorspringboot.controller.admin;

import com.danny.javablogaggregatorspringboot.entity.User;
import com.danny.javablogaggregatorspringboot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@AllArgsConstructor

@Controller
@RequestMapping("/admin/detail")
public class AdminDetailController {

    private UserService userService;

    @RequestMapping
    public String show(Model model) {
        model.addAttribute("user", userService.findAdmin());
        model.addAttribute("current", "admin-detail");
        return "admin-detail";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("admin-detail");
        }
        userService.saveAdmin(user);
        RedirectView redirectView = new RedirectView("detail?success=true");
        redirectView.setExposeModelAttributes(false);
        return new ModelAndView(redirectView);
    }
}
