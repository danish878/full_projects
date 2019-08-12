package com.danny.javablogaggregatorspringboot.controller.admin;

import com.danny.javablogaggregatorspringboot.service.ItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@AllArgsConstructor

@Controller
public class AdminItemsController {

    private ItemService itemService;

    @RequestMapping("/admin/items/toggle-enabled/{id}")
    @ResponseBody
    public String toggleEnabled(@PathVariable int id) {
        boolean enabled = itemService.toggleEnabled(id);
        return Boolean.toString(enabled);
    }
}
