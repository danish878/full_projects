package com.danny.javablogaggregatorspringboot.controller;

import com.danny.javablogaggregatorspringboot.service.ItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@AllArgsConstructor

@Controller
@RequestMapping("/social")
public class SocialController {

    private ItemService itemService;

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam int itemId, @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Inc like to item with id: {}", itemId);
        return Integer.toString(itemService.incLike(itemId));
    }

    @RequestMapping(value = "/unlike", method = RequestMethod.POST)
    @ResponseBody
    public String unlike(@RequestParam int itemId, @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Dec like to item with id: {}", itemId);
        return Integer.toString(itemService.decLike(itemId));
    }

    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam int itemId, @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Inc dislike to item with id: {}", itemId);
        return Integer.toString(itemService.incDislike(itemId));
    }

    @RequestMapping(value = "/undislike", method = RequestMethod.POST)
    @ResponseBody
    public String undislike(@RequestParam int itemId, @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Dec dislike to item with id: {}", itemId);
        return Integer.toString(itemService.decDislike(itemId));
    }

}