package com.danny.lab4sentence.dao;

import com.danny.lab4sentence.domain.Word;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("ARTICLE")
public interface ArticleClient {

	@GetMapping("/")
	Word getWord();

}
