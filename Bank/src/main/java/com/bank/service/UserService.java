package com.bank.service;

import com.bank.domain.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user);
}
