package com.bookstore.service;

import java.util.List;

import com.bookstore.domain.Book;

public interface BookService {
    List<Book> findAll();

    Book findById(Long id);

    List<Book> findByCategory(String category);

    List<Book> blurrySearch(String title);
}
