package com.danny.lab4subject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RefreshScope
public class WordsController {

    private final Random random = new Random();

    @Value("${words}") String[] words;

    @GetMapping("/")
    public Word getWord() {
        return new Word(words[random.nextInt(words.length)]);
    }
}
