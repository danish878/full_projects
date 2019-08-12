package com.danny.jba.service;

import com.danny.jba.entity.Blog;
import com.danny.jba.entity.Role;
import com.danny.jba.entity.User;
import com.danny.jba.repository.BlogRepository;
import com.danny.jba.repository.RoleRepository;
import com.danny.jba.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@AllArgsConstructor

@Transactional
@Service
public class InitDbService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BlogRepository blogRepository;
//    private ItemRepository itemRepository;

    @PostConstruct
    public void init() {

        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);

            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);

            User userAdmin = new User();
            userAdmin.setUsername("admin");
            userAdmin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userAdmin.setEnabled(true);
            userAdmin.getRoles().add(roleAdmin);
            userAdmin.getRoles().add(roleUser);
            userRepository.save(userAdmin);

            Blog blogJavaVids = new Blog();
            blogJavaVids.setName("JavaVids");
            blogJavaVids.setUrl("http://feeds.feedburner.com/javavids?format=xml");
            blogJavaVids.setUser(userAdmin);
            blogRepository.save(blogJavaVids);

            Blog blogJavaWorld = new Blog();
            blogJavaWorld.setName("JavaWorld");
            blogJavaWorld.setUrl("https://www.javaworld.com/category/core-java/index.rss");
            blogJavaWorld.setUser(userAdmin);
            blogRepository.save(blogJavaWorld);

//            Item item1 = new Item();
//            item1.setBlog(blogJavaVids);
//            item1.setTitle("first");
//            item1.setLink("http://www.javavids.com");
//            item1.setPublishedDate(new Date());
//            itemRepository.save(item1);
//
//            Item item2 = new Item();
//            item2.setBlog(blogJavaVids);
//            item2.setTitle("second");
//            item2.setLink("http://www.javavids.com");
//            item2.setPublishedDate(new Date());
//            itemRepository.save(item2);
        }
    }
}
