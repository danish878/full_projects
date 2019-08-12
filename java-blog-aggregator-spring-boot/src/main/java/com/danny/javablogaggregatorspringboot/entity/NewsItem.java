package com.danny.javablogaggregatorspringboot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Getter
@Setter

@Entity
@Table(name = "news_item")
public class NewsItem {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 500)
    private String title;

    @Column(name = "short_description", length = 500)
    private String shortDescription;

    @Column(length = Integer.MAX_VALUE)
    @Lob
    private String description;

    @Column(name = "published_date")
    private Date publishedDate;

    @Column(name = "short_name", length = 500, unique = true)
    private String shortName;

}
