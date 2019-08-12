package com.danny.javablogaggregatorspringboot.repository;

import com.danny.javablogaggregatorspringboot.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
