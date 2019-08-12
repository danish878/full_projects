package com.danny.javablogaggregatorspringboot.controller;

import com.danny.javablogaggregatorspringboot.exception.PageNotFoundException;
import com.danny.javablogaggregatorspringboot.service.BlogService;
import com.danny.javablogaggregatorspringboot.service.ConfigurationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor

@Controller
@RequestMapping(value = "/spring/icon", produces = MediaType.IMAGE_PNG_VALUE)
public class IconController {

    private BlogService blogService;
    private ConfigurationService configurationService;

    @ExceptionHandler(PageNotFoundException.class)
    public void pageNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @ResponseBody
    @RequestMapping
    public byte[] getIcon() {
        return configurationService.find().getIcon();
    }

    @ResponseBody
    @RequestMapping(value = "/{blogId}")
    public byte[] getBlogIcon(@PathVariable int blogId) throws IOException {
        try {
            return blogService.getIcon(blogId);
        } catch (PageNotFoundException e) {
            log.error("Icon not found! Blog id: {}", blogId);
            throw e;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/favicon")
    public byte[] getFavicon() {
        return configurationService.find().getFavicon();
    }

    @ResponseBody
    @RequestMapping(value = "/appleTouchIcon")
    public byte[] getAppleTouchIcon() {
        return configurationService.find().getAppleTouchIcon();
    }

}