package com.danny.javablogaggregatorspringboot.service;

import com.danny.javablogaggregatorspringboot.entity.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class AllCategoriesService {

    private CategoryService categoryService;

    public Integer[] getAllCategoryIds() {
        List<Category> categories = categoryService.findAll();
        Integer[] result = new Integer[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            result[i] = categories.get(i).getId();
        }
        return result;
    }

}
