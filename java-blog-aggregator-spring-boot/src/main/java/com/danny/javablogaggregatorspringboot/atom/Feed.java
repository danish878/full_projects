package com.danny.javablogaggregatorspringboot.atom;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Feed {

    @XmlElement(name = "entry")
    private List<Entry> entries;

}
