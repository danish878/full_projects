package com.danny.lab4sentence.service;

import com.danny.lab4sentence.dao.*;
import com.danny.lab4sentence.domain.Word;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class WordServiceImpl implements WordService {

	private final VerbClient verbClient;
	private final SubjectClient subjectClient;
	private final ArticleClient articleClient;
	private final AdjectiveClient adjectiveClient;
	private final NounClient nounClient;

	@Override
	@HystrixCommand(fallbackMethod="getFallbackSubject")
	public Word getSubject() {
		return subjectClient.getWord();
	}
	
	@Override
	public Word getVerb() {
		return verbClient.getWord();
	}
	
	@Override
	public Word getArticle() {
		return articleClient.getWord();
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackAdjective")
	public Word getAdjective() {
		return adjectiveClient.getWord();
	}
	
	@Override
	@HystrixCommand(fallbackMethod="getFallbackNoun")
	public Word getNoun() {
		return nounClient.getWord();
	}

	public Word getFallbackSubject() {
		return new Word("Someone");
	}

	public Word getFallbackAdjective() {
		return new Word("");
	}

	public Word getFallbackNoun() {
		return new Word("something");
	}
}