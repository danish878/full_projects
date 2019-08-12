package com.danny.javablogaggregatorspringboot.entity;

import com.danny.javablogaggregatorspringboot.annotation.UniqueBlog;
import com.danny.javablogaggregatorspringboot.annotation.UniqueShortName;
import com.danny.javablogaggregatorspringboot.util.MyUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter

@Entity
public class Blog {

    @Id
    @GeneratedValue
    private Integer id;

    @Lob
    @Column(length = Integer.MAX_VALUE, updatable = false)
    private byte[] icon;

    @Size(min = 1, max = 1000, message = "Invalid URL!")
    @UniqueBlog(message = "This blog already exists!")
    @URL(message = "Invalid URL!")
    @Column(length = 1000, unique = true)
    private String url;

    @Size(min = 1, message = "Name must be at least 1 character!")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    private List<Item> items;

    @Size(min = 1, max = 255, message = "Short name must be between 1 and 255 characters!")
    @UniqueShortName(message = "This short name already exists!")
    @NotNull
    @Column(name = "short_name", unique = true)
    private String shortName;

    private String nick;

    @NotNull
    @Size(min = 1, message = "Homepage cannot be empty!")
    @URL(message = "Invalid URL!")
    @Column(name = "homepage")
    private String homepageUrl;

    @Column(name = "last_check_status", updatable = false)
    private Boolean lastCheckStatus;

    /**
     * Date when was some item added.
     */
    @Column(name = "last_indexed_date", updatable = false)
    private Date lastIndexedDate;

    @Lob
    @Column(name = "last_check_error_text", length = Integer.MAX_VALUE, updatable = false)
    private String lastCheckErrorText;

    @Column(name = "last_check_error_count", updatable = false)
    private Integer lastCheckErrorCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private Boolean aggregator;

    @Column(name = "min_reddit_ups")
    private Integer minRedditUps;

    @Column(updatable = false)
    private Integer popularity;

    private Boolean archived;

    public String getPublicName() {
        return MyUtil.getPublicName(nick, name, false);
    }

}
