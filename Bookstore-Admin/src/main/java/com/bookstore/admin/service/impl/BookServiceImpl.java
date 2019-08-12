package com.bookstore.admin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.admin.domain.Book;
import com.bookstore.admin.repository.BookRepository;
import com.bookstore.admin.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> findAll() {
        return (List<Book>) bookRepository.findAll();
    }

    public Book findOne(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.get();
    }

    public void removeOne(Long id) {
        bookRepository.deleteById(id);
    }
}
