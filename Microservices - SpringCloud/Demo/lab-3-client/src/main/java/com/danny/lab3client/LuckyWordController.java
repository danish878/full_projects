package com.danny.lab3client;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConfigurationProperties(prefix = "word-config")
@Setter
@Getter
public class LuckyWordController {

    //  @Value("${lucky-word}")
    String luckyWord;
    String preamble;

    @GetMapping("/lucky-word")
    public String showLuckyWord() {
        return preamble + ": " + luckyWord;
    }
}