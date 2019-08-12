package com.danny.javablogaggregatorspringboot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Getter
@Setter

@Entity
public class Category {

    @Id
    @GeneratedValue
    private int id;

    private String name;

    @Column(name = "short_name")
    private String shortName;

    @OneToMany(mappedBy = "category")
    private List<Blog> blogs;

    @Transient
    private int blogCount;

}
