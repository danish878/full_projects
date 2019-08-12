package com.danny.javablogaggregatorspringboot.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MyUtilTest {

    @Test
    public void generatePermalink() {
        String link = "https://www.youtube.com/";
        String permalink = MyUtil.generatePermalink(link);
    }

    @Test
    public void getPublicName() {
    }
}