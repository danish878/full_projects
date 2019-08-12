package com.danny.javablogaggregatorspringboot.atom;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.datatype.XMLGregorianCalendar;

@Getter
@Setter

@XmlAccessorType(XmlAccessType.FIELD)
public class Entry {

    private String title;

    @XmlElement(name = "link")
    private List<Link> links;

    @XmlElement(namespace = "http://rssnamespace.org/feedburner/ext/1.0")
    private String origLink;

    private XMLGregorianCalendar updated;
    private XMLGregorianCalendar published;
    private String content;
    private String summary;

}
