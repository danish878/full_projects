package com.danny.javablogaggregatorspringboot.rss;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Getter
@Setter

@XmlAccessorType(XmlAccessType.FIELD)
public class TRssChannel {

    private String title;
    private String link;
    private String description;
    private String lastBuildDate;

    @XmlElement(name = "item")
    List<TRssItem> items;

}
