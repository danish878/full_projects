package com.danny.javablogaggregatorspringboot.repository;

import com.danny.javablogaggregatorspringboot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findByShortName(String shortName);

}
