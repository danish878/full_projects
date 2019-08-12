package com.bank.dao;

import org.springframework.data.repository.CrudRepository;

import com.bank.domain.Account;

public interface AccountDao extends CrudRepository<Account, Long> {

    Account findByNumber(int number);
}
