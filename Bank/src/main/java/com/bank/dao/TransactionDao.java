package com.bank.dao;

import org.springframework.data.repository.CrudRepository;

import com.bank.domain.Transaction;

public interface TransactionDao extends CrudRepository<Transaction, Long> {

}
