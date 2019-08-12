package com.danny.javablogaggregatorspringboot.controller.admin;

import com.danny.javablogaggregatorspringboot.entity.NewsItem;
import com.danny.javablogaggregatorspringboot.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@AllArgsConstructor

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

    private NewsService newsService;

    @ModelAttribute
    public NewsItem construct() {
        return new NewsItem();
    }

    @RequestMapping("/add")
    public String showAdd(Model model) {
        NewsItem newsItem = new NewsItem();
        newsItem.setDescription("<div class='jumbotron'>\n\n</div>\n");
        model.addAttribute("newsItem", newsItem);
        model.addAttribute("current", "news-form");
        return "news-form";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public View insert(@Valid @ModelAttribute NewsItem newsItem) {
        newsService.save(newsItem);
        RedirectView redirectView = new RedirectView("../news/add?success=true");
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }

    @RequestMapping("/edit/{shortName}")
    public String showEdit(Model model, @PathVariable String shortName) {
        model.addAttribute("newsItem", newsService.findOne(shortName));
        return "news-form";
    }

    @RequestMapping(value = "/edit/{shortName}", method = RequestMethod.POST)
    public View edit(@Valid @ModelAttribute NewsItem newsItem, @PathVariable("shortName") String shortName) {
        newsService.save(newsItem);
        RedirectView redirectView = new RedirectView(newsItem.getShortName() + "?success=true");
        redirectView.setExposeModelAttributes(false);
        return redirectView;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public void delete(@PathVariable int id) {
        newsService.delete(id);
    }

}
