package com.danny.jba.repository;

import com.danny.jba.entity.Blog;
import com.danny.jba.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    List<Blog> findByUser(User user);
}
