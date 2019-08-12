package com.bookstore;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.domain.User;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.service.UserService;
import com.bookstore.utility.SecurityUtility;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//		User user = new User();
//		user.setFirstName("Danish");
//		user.setLastName("Daood");
//		user.setUsername("Danny");
//		user.setPassword(SecurityUtility.passwordEncoder().encode("123"));
//		user.setEmail("danny@gmail.com");
//		user.setPhone("0322-4406886");
//		
//		Set<UserRole> userRoles = new HashSet<>();
//		
//		Role role= new Role();
//		role.setRoleId(1);
//		role.setName("ROLE_USER");
//		
//		userRoles.add(new UserRole(user, role));
//		
//		userService.createUser(user, userRoles);
    }

}
