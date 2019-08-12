package com.danny.javablogaggregatorspringboot.service;

import com.danny.javablogaggregatorspringboot.entity.Configuration;
import com.danny.javablogaggregatorspringboot.entity.NewsItem;
import com.danny.javablogaggregatorspringboot.repository.NewsItemRepository;
import com.danny.javablogaggregatorspringboot.util.MyUtil;
import cz.jiripinkas.jsitemapgenerator.WebPage;
import cz.jiripinkas.jsitemapgenerator.generator.RssGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor

@Service
public class NewsService {

    private static final int PAGE_SIZE = 10;

    private final NewsItemRepository newsItemRepository;
    private final ConfigurationService configurationService;

    public void save(NewsItem newsItem) {
        newsItem.setPublishedDate(new Date());
        if (newsItem.getShortName() == null || newsItem.getShortName().isEmpty()) {
            newsItem.setShortName(MyUtil.generatePermalink(newsItem.getTitle()));
        }
        newsItemRepository.save(newsItem);
    }

    public Page<NewsItem> findNews(int page) {
        return newsItemRepository.findAll(PageRequest.of(page, PAGE_SIZE, Direction.DESC, "publishedDate"));
    }

    public List<NewsItem> findAll() {
        return newsItemRepository.findAll();
    }

    @Transactional
    public NewsItem findOne(String shortName) {
        return newsItemRepository.findByShortName(shortName);
    }

    public String getFeed() {
        Configuration configuration = configurationService.find();
        Page<NewsItem> firstTenNews = findNews(0);
        return RssGenerator
                .of(configuration.getChannelLink(), configuration.getChannelTitle(), configuration.getChannelDescription())
                .defaultDir("news")
                .addPages(firstTenNews.getContent(), newsItem -> {
                    return WebPage.rssBuilder()
                            .title(newsItem.getTitle())
                            .description(newsItem.getShortDescription())
                            .link(newsItem.getShortName())
                            .pubDate(newsItem.getPublishedDate())
                            .build();
                })
                .toString();
    }

    public void delete(int id) {
        newsItemRepository.deleteById(id);
    }

}