package com.danny.javablogaggregatorspringboot.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.danny.javablogaggregatorspringboot.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueBlogValidator implements ConstraintValidator<UniqueBlog, String> {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public void initialize(UniqueBlog constraintAnnotation) {
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (blogRepository == null) {
            return true;
        }
        return blogRepository.findByUrl(url) == null;
    }

}
