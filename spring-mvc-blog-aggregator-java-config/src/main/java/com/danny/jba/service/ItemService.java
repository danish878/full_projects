package com.danny.jba.service;

import com.danny.jba.entity.Item;
import com.danny.jba.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public List<Item> getItems() {
        return itemRepository.findAll(PageRequest.of(0, 30, Sort.Direction.DESC, "publishedDate")).getContent();
    }
}
