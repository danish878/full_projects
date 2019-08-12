package com.danny.javablogaggregatorspringboot.annotation;

import com.danny.javablogaggregatorspringboot.repository.BlogRepository;
import com.danny.javablogaggregatorspringboot.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class UniqueShortNameValidator implements ConstraintValidator<UniqueShortName, String> {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public void initialize(UniqueShortName constraintAnnotation) {
    }

    @Override
    public boolean isValid(String shortName, ConstraintValidatorContext context) {
        if (blogRepository == null) {
            return true;
        }
        return blogRepository.findByShortName(MyUtil.generatePermalink(shortName)) == null;
    }

}
