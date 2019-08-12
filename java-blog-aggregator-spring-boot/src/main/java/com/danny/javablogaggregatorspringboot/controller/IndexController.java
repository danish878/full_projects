package com.danny.javablogaggregatorspringboot.controller;

import com.danny.javablogaggregatorspringboot.dto.ItemDto;
import com.danny.javablogaggregatorspringboot.service.AllCategoriesService;
import com.danny.javablogaggregatorspringboot.service.ConfigurationService;
import com.danny.javablogaggregatorspringboot.service.ItemService;
import com.danny.javablogaggregatorspringboot.service.ItemService.MaxType;
import com.danny.javablogaggregatorspringboot.service.ItemService.OrderType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor

@Controller
public class IndexController {

    private ItemService itemService;
    private AllCategoriesService allCategoriesService;
    private ConfigurationService configurationService;

    @RequestMapping("/404")
    public String error404() {
        return "404";
    }

    /**
     * This method is called from index.html to get all itemIds from list of Items
     *
     * @return All itemIds from list of Items
     */
    public List<Integer> getItemIds(List<ItemDto> items) {
        return items.stream().map(ItemDto::getId).collect(Collectors.toList());
    }

    private String showFirstPage(Model model, HttpServletRequest request, String selectedCategoriesString) {
        return showPage(model, request, 0, selectedCategoriesString);
    }

    private String showPage(Model model, HttpServletRequest request, int page, String selectedCategoriesString) {
        boolean showAll = false;
        if (request.isUserInRole("ADMIN")) {
            showAll = true;
        }
        model.addAttribute("items", itemService.getDtoItems(page, showAll, OrderType.LATEST, MaxType.UNDEFINED, getSelectedCategories(selectedCategoriesString)));
        model.addAttribute("nextPage", page + 1);
        return "index";
    }

    private Integer[] getSelectedCategories(String selectedCategoriesString) {
        Integer[] selectedCategories;
        if (selectedCategoriesString == null) {
            selectedCategories = allCategoriesService.getAllCategoryIds();
        } else {
            String[] strings = selectedCategoriesString.replace("[", "").replace("]", "").split(",");
            List<Integer> selectedCategoriesList = new ArrayList<>();
            for (String string : strings) {
                if (!string.trim().isEmpty()) {
                    selectedCategoriesList.add(Integer.parseInt(string.trim()));
                }
            }
            selectedCategories = selectedCategoriesList.toArray(new Integer[]{});
        }
        return selectedCategories;
    }

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request,
                        @CookieValue(value = "selectedCategories", required = false) String selectedCategoriesString,
                        @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Navigated to homepage with selectedCategories: {}", selectedCategoriesString);
        model.addAttribute("title", configurationService.find().getHomepageHeading());
        return showFirstPage(model, request, selectedCategoriesString);
    }

    @RequestMapping(value = "/", params = "page")
    public String index(Model model, @RequestParam int page, HttpServletRequest request,
                        @CookieValue(required = false) String selectedCategoriesString,
                        @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Navigated to homepage with selectedCategories: {}, page: {}", selectedCategoriesString, page);
        model.addAttribute("title", configurationService.find().getHomepageHeading());
        return showPage(model, request, page, selectedCategoriesString);
    }

    @ResponseBody
    @RequestMapping("/page/{page}")
    public List<ItemDto> getPageLatest(@PathVariable int page, HttpServletRequest request, @RequestParam Integer[] selectedCategories, @RequestParam(required = false) String search,
                                       @RequestParam(required = false) String orderBy, @RequestParam(required = false) String shortName, @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Navigated to JSON, page {} with selectedCategories: {}", page, Arrays.asList(selectedCategories));
        if (search != null && !search.trim().isEmpty()) {
            log.info("search for: {}", search);
        }
        boolean showAll = false;
        if (request.isUserInRole("ADMIN")) {
            showAll = true;
        }
        if ("topWeek".equals(orderBy)) {
            return itemService.getDtoItems(page, showAll, OrderType.MOST_VIEWED, MaxType.WEEK, selectedCategories, search, shortName);
        } else if ("topMonth".equals(orderBy)) {
            return itemService.getDtoItems(page, showAll, OrderType.MOST_VIEWED, MaxType.MONTH, selectedCategories, search, shortName);
        } else {
            return itemService.getDtoItems(page, showAll, OrderType.LATEST, MaxType.UNDEFINED, selectedCategories, search, shortName);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/inc-count", method = RequestMethod.POST)
    public String incItemCount(@RequestParam int itemId, @RequestHeader(value = "User-Agent", required = false) String userAgent) {
        log.info("UA: {}", userAgent);
        log.info("Inc count to item with id: {}", itemId);
        return Integer.toString(itemService.incCount(itemId));
    }

}