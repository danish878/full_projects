package com.danny.jba.controller;

import com.danny.jba.entity.Blog;
import com.danny.jba.service.BlogService;
import com.danny.jba.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@AllArgsConstructor

@Controller
public class UserController {

    private UserService userService;
    private BlogService blogService;

    @ModelAttribute("blog")
    public Blog constructBlog() {
        return new Blog();
    }

    @GetMapping("/account")
    public String account(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("user", userService.findByNameWithBlogs(username));
        return "account";
    }

    @PostMapping("/account")
    public String addBlog(@Valid @ModelAttribute("blog") Blog blog, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors())
            return account(model, principal);

        blogService.save(blog, principal.getName());
        return "redirect:/account.html";
    }

    @GetMapping("/blog/remove/{id}")
    public String removeBlog(@PathVariable int id) {
        Blog blog = blogService.findById(id);
        blogService.delete(blog);
        return "redirect:/account.html";
    }

}