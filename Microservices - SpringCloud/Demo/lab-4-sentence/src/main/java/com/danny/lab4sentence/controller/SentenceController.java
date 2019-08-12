package com.danny.lab4sentence.controller;

import com.danny.lab4sentence.service.SentenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
public class SentenceController {

//    @Autowired
//    private DiscoveryClient client;
//
//    @Autowired
//    private RestTemplate template;

    private final SentenceService sentenceService;

    /**
     * Display a small list of Sentences to the caller:
     */
    @GetMapping("/sentence")
    public @ResponseBody
    String getSentence() {
        long start = System.currentTimeMillis();
        String output =
                "<h3>Some Sentences</h3><br/>" +
                        sentenceService.buildSentence() + "<br/><br/>" +
                        sentenceService.buildSentence() + "<br/><br/>" +
                        sentenceService.buildSentence() + "<br/><br/>" +
                        sentenceService.buildSentence() + "<br/><br/>" +
                        sentenceService.buildSentence() + "<br/><br/>";
        long end = System.currentTimeMillis();
        return output + "Elapsed time (ms): " + (end - start);
    }

//    /**
//     * Assemble a sentence by gathering random words of each part of speech:
//     */
//    public String buildSentence() {
//        String sentence = "There was a problem assembling the sentence!";
//        try{
//            sentence =
//                    String.format("%s %s %s %s %s.",
//                            getWord("subject"),
//                            getWord("verb"),
//                            getWord("article"),
//                            getWord("adjective"),
//                            getWord("noun") );
//        } catch ( Exception e ) {
//            System.out.println(e);
//        }
//        return sentence;
//    }


//    /**
//     * Obtain a random word for a given part of speech, where the part
//     * of speech is indicated by the given service / client ID:
//     */
//    private String getWord(String service) {
//        List<ServiceInstance> list = client.getInstances(service);
//        if (list != null && list.size() > 0 ) {
//            URI uri = list.get(0).getUri();
//            if (uri !=null ) {
//                System.out.println("******************************************************************URI: "+uri);
//                return (new RestTemplate()).getForObject(uri,String.class);
//            }
//        }
//        return null;
//    }

//    public String getWord(String service) {
//        System.out.println("************************************************************************http://" + service);
//        return template.getForObject("http://" + service, String.class);
//    }
}
