package com.danny.javablogaggregatorspringboot.controller;

import com.danny.javablogaggregatorspringboot.service.AllCategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor

@Controller
public class CategoryController {

    private AllCategoriesService allCategoriesService;

    @RequestMapping("/all-categories")
    @ResponseBody
    public Integer[] getCategories() {
        return allCategoriesService.getAllCategoryIds();
    }

}
