package com.danny.javablogaggregatorspringboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Getter
@Setter

@Entity
public class Configuration {

    @Id
    @GeneratedValue
    private int id;

    private String title;

    @Column(name = "brand_name")
    private String brandName;

    @Column(length = Integer.MAX_VALUE)
    @Lob
    private String footer;

    @Column(name = "google_adsense", length = Integer.MAX_VALUE)
    @Lob
    private String googleAdsense;

    @Column(name = "google_analytics", length = Integer.MAX_VALUE)
    @Lob
    private String googleAnalytics;

    @Column(name = "homepage_heading")
    private String homepageHeading;

    @Column(name = "top_heading")
    private String topHeading;

    @Column(name = "channel_title")
    private String channelTitle;

    @Column(name = "channel_link")
    private String channelLink;

    @Column(name = "channel_description")
    private String channelDescription;

    @Lob
    @Column(name = "news_social_buttons", length = Integer.MAX_VALUE)
    private String newsSocialButtons;

    @Lob
    @Column(name = "disqus_code", length = Integer.MAX_VALUE)
    private String disqusCode;

    @Lob
    @Column(name = "twitter_oauth", length = Integer.MAX_VALUE)
    private String twitterOauth;

    @Lob
    @Column(length = Integer.MAX_VALUE, updatable = false)
    private byte[] icon;

    @Lob
    @Column(length = Integer.MAX_VALUE, updatable = false)
    private byte[] favicon;

    @Lob
    @Column(length = Integer.MAX_VALUE, name = "apple_touch_icon", updatable = false)
    private byte[] appleTouchIcon;

}