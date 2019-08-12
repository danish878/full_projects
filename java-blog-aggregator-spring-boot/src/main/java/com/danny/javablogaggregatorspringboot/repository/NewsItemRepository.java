package com.danny.javablogaggregatorspringboot.repository;

import com.danny.javablogaggregatorspringboot.entity.NewsItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsItemRepository extends JpaRepository<NewsItem, Integer> {

    NewsItem findByShortName(String shortName);

}
