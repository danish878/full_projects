package com.bank.dao;

import org.springframework.data.repository.CrudRepository;

import com.bank.domain.User;

public interface UserDao extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);
}
