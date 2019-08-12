package com.danny.javablogaggregatorspringboot.controller.admin;

import com.danny.javablogaggregatorspringboot.service.BlogService;
import com.danny.javablogaggregatorspringboot.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@AllArgsConstructor

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {

    private UserService userService;
    private BlogService blogService;

    @RequestMapping
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("current", "users");
        return "users";
    }

    @RequestMapping("/{id}")
    public String detail(Model model, @PathVariable int id) {
        model.addAttribute("user", userService.findOneWithBlogs(id));
        return "user-detail";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public void removeUser(@PathVariable int id) {
        userService.delete(id);
    }

    @RequestMapping(value = "/upload-icon/{id}", method = RequestMethod.POST)
    public View uploadIcon(@RequestParam MultipartFile icon, @PathVariable("id") int blogId) {
        if (!icon.isEmpty()) {
            try {
                blogService.saveIcon(blogId, icon.getBytes());
            } catch (Exception e) {
                log.error("could not upload icon", e);
            }
        } else {
            log.error("could not upload icon");
        }
        RedirectView redirectView = new RedirectView("/blog-form?blogId=" + blogId + "&success=true");
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }

}