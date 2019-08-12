package com.danny.jba.service;

import com.danny.jba.entity.Blog;
import com.danny.jba.entity.Item;
import com.danny.jba.entity.User;
import com.danny.jba.exception.RssException;
import com.danny.jba.repository.BlogRepository;
import com.danny.jba.repository.ItemRepository;
import com.danny.jba.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Transactional
@Service
public class BlogService {

    private UserRepository userRepository;
    private BlogRepository blogRepository;
    private ItemRepository itemRepository;
    private RssService rssService;

    // 1hour = 60 seconds * 60 minutes * 1000 milliseconds
    @Scheduled(fixedDelay = 3600000)
    public void reloadBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        blogs.forEach(this::saveItems);
    }

    public void saveItems(Blog blog) {
        try {
            List<Item> items = rssService.getItems(blog.getUrl());
            items.forEach(item -> {
                Item savedItem = itemRepository.findByBlogAndLink(blog, item.getLink());
                if (savedItem == null) {
                    item.setBlog(blog);
                    itemRepository.save(item);
                }
            });
        } catch (RssException e) {
            e.printStackTrace();
        }
    }

    public void save(Blog blog, String username) {
        User user = userRepository.findByUsername(username);
        blog.setUser(user);
        blogRepository.save(blog);
        saveItems(blog);
    }

//    public void deleteById(int id) {
//        blogRepository.deleteById(id);
//    }

    public Blog findById(int id) {
        Optional<Blog> blog = blogRepository.findById(id);
        return blog.orElse(null);
    }

    @PreAuthorize("#blog.user.username == authentication.name OR hasRole('ROLE_ADMIN')")
    public void delete(@P("blog") Blog blog) {
        blogRepository.delete(blog);
    }
}