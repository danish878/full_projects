package com.bookstore.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long> {

}
