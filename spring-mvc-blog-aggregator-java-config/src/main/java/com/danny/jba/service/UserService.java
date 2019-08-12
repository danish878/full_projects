package com.danny.jba.service;

import com.danny.jba.entity.Blog;
import com.danny.jba.entity.Item;
import com.danny.jba.entity.User;
import com.danny.jba.repository.BlogRepository;
import com.danny.jba.repository.ItemRepository;
import com.danny.jba.repository.RoleRepository;
import com.danny.jba.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor

@Transactional
@Service
public class UserService {

    private UserRepository userRepository;
    private BlogRepository blogRepository;
    private ItemRepository itemRepository;
    private RoleRepository roleRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public User findByIdWithBlogs(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.orElse(null);
        List<Blog> blogs = blogRepository.findByUser(user);
        for (Blog blog : blogs) {
//            List<Item> items = itemRepository.findByBlog(blog, new PageRequest(0, 10, Sort.Direction.DESC, "publishedDate"));
            List<Item> items = itemRepository.findByBlog(blog, PageRequest.of(0, 10, Sort.Direction.DESC, "publishedDate"));
//            blog.getItems().addAll(items);
            blog.setItems(items);
        }
//        user.getBlogs().addAll(blogs);
        if (user != null) {
            user.setBlogs(blogs);
        }
        return user;
    }

    public void save(User user) {
        user.setEnabled(true);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.getRoles().add(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }

    public User findByNameWithBlogs(String name) {
        User user = userRepository.findByUsername(name);

        return findByIdWithBlogs(user.getId());
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}