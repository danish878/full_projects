package com.danny.javablogaggregatorspringboot.service;

import com.danny.javablogaggregatorspringboot.dto.CategoryDto;
import com.danny.javablogaggregatorspringboot.entity.Blog;
import com.danny.javablogaggregatorspringboot.entity.Category;
import com.danny.javablogaggregatorspringboot.repository.BlogRepository;
import com.danny.javablogaggregatorspringboot.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;
    private final MapperFacade mapperFacade;

    @Cacheable("categories")
    public List<Category> findAll() {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            category.setBlogCount(blogRepository.countByCategoryId(category.getId()));
        }
        return categories;
    }

    @CacheEvict(value = "categories", allEntries = true)
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @CacheEvict(value = "categories", allEntries = true)
    public void delete(int id) {
        categoryRepository.deleteById(id);
    }

    public CategoryDto findOneDto(int id) {
        return mapperFacade.map(categoryRepository.findById(id).get(), CategoryDto.class);
    }

    @CacheEvict(value = "blogCountUnapproved", allEntries = true)
    public void addMapping(int blogId, int categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        Blog blog = blogRepository.findById(blogId).get();
        blog.setCategory(category);
        blogRepository.save(blog);
    }

}