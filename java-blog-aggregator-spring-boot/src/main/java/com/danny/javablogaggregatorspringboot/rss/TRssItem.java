package com.danny.javablogaggregatorspringboot.rss;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter

@XmlAccessorType(XmlAccessType.FIELD)
public class TRssItem {

    private String title;
    private String description;
    private String pubDate;
    private String link;

    @XmlElement(namespace = "http://rssnamespace.org/feedburner/ext/1.0")
    private String origLink;

    @XmlElement(namespace = "http://purl.org/rss/1.0/modules/content/")
    private String encoded;

}
