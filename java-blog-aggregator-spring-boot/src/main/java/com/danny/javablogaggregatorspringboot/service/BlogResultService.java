package com.danny.javablogaggregatorspringboot.service;

import com.danny.javablogaggregatorspringboot.entity.Blog;
import com.danny.javablogaggregatorspringboot.repository.BlogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor

@Service
public class BlogResultService {

    private BlogRepository blogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOk(Blog blog) {
        blogRepository.saveOk(blog.getId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLastIndexedDate(Blog blog) {
        blogRepository.saveLastIndexedDate(blog.getId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveFail(Blog blog, String errorText) {
        int errorCount = 0;
        if (blog.getLastCheckErrorCount() != null) {
            errorCount = blog.getLastCheckErrorCount();
        }
        errorCount++;
        blogRepository.saveFail(blog.getId(), errorCount, errorText);
    }

}
