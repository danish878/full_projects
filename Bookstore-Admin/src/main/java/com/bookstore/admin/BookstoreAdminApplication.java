package com.bookstore.admin;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bookstore.admin.domain.User;
import com.bookstore.admin.domain.security.Role;
import com.bookstore.admin.domain.security.UserRole;
import com.bookstore.admin.service.UserService;
import com.bookstore.admin.utility.SecurityUtility;

@SpringBootApplication
public class BookstoreAdminApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BookstoreAdminApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        User user = new User();
        user.setUsername("admin");
        user.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
        user.setEmail("danishdaood@gmail.com");

        Set<UserRole> userRoles = new HashSet<>();

        Role role = new Role();
        role.setRoleId(0);
        role.setName("ROLE_ADMIN");

        userRoles.add(new UserRole(user, role));

        userService.createUser(user, userRoles);
    }

}
