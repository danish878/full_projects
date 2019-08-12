package com.danny.javablogaggregatorspringboot.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Getter
@Setter

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 1000)
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(length = Integer.MAX_VALUE)
    private String description;

    /**
     * When was the item published by it's owner
     */
    @Column(name = "published_date")
    private Date publishedDate;

    @Column(length = 1000)
    private String link;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private boolean enabled;

    @Column(name = "click_count", nullable = false)
    private Integer clickCount;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount;

    @Column(name = "dislike_count", nullable = false)
    private Integer dislikeCount;

    @Column(name = "twitter_retweet_count", nullable = false)
    private Integer twitterRetweetCount;

    @Column(name = "facebook_share_count", nullable = false)
    private Integer facebookShareCount;

    @Column(name = "linkedin_share_count", nullable = false)
    private Integer linkedinShareCount;

    /**
     * When was the item saved to local database
     */
    @Column(name = "saved_date")
    private Date savedDate;

    @Transient
    private String error;

    public Item() {
        setEnabled(true);
        setClickCount(0);
        setLikeCount(0);
        setDislikeCount(0);
        setTwitterRetweetCount(0);
        setFacebookShareCount(0);
        setLinkedinShareCount(0);
    }

}
