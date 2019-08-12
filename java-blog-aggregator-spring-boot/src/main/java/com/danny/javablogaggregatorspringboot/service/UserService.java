package com.danny.javablogaggregatorspringboot.service;

import com.danny.javablogaggregatorspringboot.entity.Blog;
import com.danny.javablogaggregatorspringboot.entity.Role;
import com.danny.javablogaggregatorspringboot.entity.User;
import com.danny.javablogaggregatorspringboot.repository.BlogRepository;
import com.danny.javablogaggregatorspringboot.repository.RoleRepository;
import com.danny.javablogaggregatorspringboot.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BlogRepository blogRepository;

    public List<User> findAll() {
        return userRepository.findAllFetchRoles();
    }

    @Transactional
    public User findOneWithBlogs(int id) {
        User user = userRepository.findById(id).get();
        List<Blog> blogs = blogRepository.findByUserId(id);
        user.setBlogs(blogs);
        return user;
    }

    @CacheEvict(value = "userCount", allEntries = true)
    public void save(User user) {
        user.setEnabled(true);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_USER"));
        user.setRoles(roles);

        userRepository.save(user);
    }

    /**
     * Called from admin-detail.html
     *
     * @param user user
     */
    public void saveAdmin(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        userRepository.updateAdminPassword(encodedPassword);
        userRepository.updateAdminName(user.getName());
    }

    public User findOneWithBlogs(String name) {
        User user = userRepository.findByName(name);
        return findOneWithBlogs(user.getId());
    }

    @CacheEvict(value = "userCount", allEntries = true)
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User findOne(String username) {
        return userRepository.findByName(username);
    }

    @Cacheable("userCount")
    public long count() {
        return userRepository.count();
    }

    public User findAdmin() {
        return userRepository.findAdmin();
    }

}