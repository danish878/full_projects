package com.danny.lab4sentence.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class SentenceServiceImpl implements SentenceService {

//    private final VerbClient verbService;
//    private final SubjectClient subjectService;
//    private final ArticleClient articleService;
//    private final AdjectiveClient adjectiveService;
//    private final NounClient nounService;

    private final WordService wordService;

    /**
     * Assemble a sentence by gathering random words of each part of speech:
     */
//    public String buildSentence() {
//        String sentence = "There was a problem assembling the sentence!";
//        sentence =
//                String.format("%s %s %s %s %s.",
//                        subjectService.getWord().getString(),
//                        verbService.getWord().getString(),
//                        articleService.getWord().getString(),
//                        adjectiveService.getWord().getString(),
//                        nounService.getWord().getString());
//        return sentence;
//    }
    public String buildSentence() {
        return
                String.format("%s %s %s %s %s.",
                        wordService.getSubject().getString(),
                        wordService.getVerb().getString(),
                        wordService.getArticle().getString(),
                        wordService.getAdjective().getString(),
                        wordService.getNoun().getString())
                ;
    }
}