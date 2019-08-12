package com.danny.javablogaggregatorspringboot.controller;

import com.danny.javablogaggregatorspringboot.entity.NewsItem;
import com.danny.javablogaggregatorspringboot.exception.PageNotFoundException;
import com.danny.javablogaggregatorspringboot.service.NewsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor

@Controller
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    @ExceptionHandler(PageNotFoundException.class)
    public void pageNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @RequestMapping
    public String showNews(Model model, @RequestParam(defaultValue = "0") int page, @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Navigated to news list");
        model.addAttribute("newsPage", newsService.findNews(page));
        model.addAttribute("currPage", page);
        model.addAttribute("current", "news");
        return "news";
    }

    @RequestMapping("/{shortName}")
    public String showDetail(Model model, @PathVariable String shortName, @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Navigated to news: {}", shortName);
        NewsItem newsItem = newsService.findOne(shortName);
        if (newsItem == null) {
            log.error("News not found: {}", shortName);
            throw new PageNotFoundException();
        }
        model.addAttribute("news", newsItem);
        model.addAttribute("current", "news");
        return "news-detail";
    }

    @ResponseBody
    @RequestMapping(path = "/feed.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String rss(@RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Navigated to rss feed");
        return newsService.getFeed();
    }

}
