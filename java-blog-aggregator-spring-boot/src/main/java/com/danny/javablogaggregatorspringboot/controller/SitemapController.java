package com.danny.javablogaggregatorspringboot.controller;

import com.danny.javablogaggregatorspringboot.service.BlogService;
import com.danny.javablogaggregatorspringboot.service.ConfigurationService;
import com.danny.javablogaggregatorspringboot.service.NewsService;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.SitemapGenerator;
import cz.jiripinkas.jsitemapgenerator.robots.RobotsRule;
import cz.jiripinkas.jsitemapgenerator.robots.RobotsTxtGenerator;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor

@Controller
public class SitemapController {

    private ConfigurationService configurationService;
    private BlogService blogService;
    private NewsService newsService;

    @ResponseBody
    @RequestMapping(path = "/robots.txt", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getRobots() {
        return RobotsTxtGenerator.of(configurationService.find().getChannelLink())
                .addSitemap("sitemap.xml")
                .addRule(RobotsRule.builder().userAgentAll().allowAll().build())
                .toString();
    }

    @ResponseBody
    @RequestMapping(path = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String getSitemap() {
        return SitemapGenerator.of(configurationService.find().getChannelLink())
                .addPage(WebPage.builder().nameRoot().build())
                .addPage(WebPage.of("blogs"))
                .addPage(WebPage.of("news"))
                .defaultDir("blog")
                .addPages(blogService.findAll(false), blog -> WebPage.of(blog.getShortName()))
                .defaultDir("news")
                .addPages(newsService.findAll(), newsItem -> WebPage.of(newsItem.getShortName()))
                .toString();
    }

}