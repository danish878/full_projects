package com.bookstore.admin.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.admin.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByname(String name);
}
