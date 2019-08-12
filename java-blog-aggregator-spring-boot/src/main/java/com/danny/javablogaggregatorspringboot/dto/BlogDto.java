package com.danny.javablogaggregatorspringboot.dto;

import com.danny.javablogaggregatorspringboot.util.MyUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogDto {

    private String name;
    private String nick;
    private String shortName;
    private int id;
    private CategoryDto category;

    public String getPublicName() {
        return MyUtil.getPublicName(nick, name, true);
    }

}
