package com.bookstore.admin.service;

import java.util.Set;

import com.bookstore.admin.domain.User;
import com.bookstore.admin.domain.security.UserRole;

public interface UserService {
    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);
}
