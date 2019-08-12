package com.bank.dao;

import org.springframework.data.repository.CrudRepository;

import com.bank.domain.Recipient;

public interface RecipientDao extends CrudRepository<Recipient, Long> {

}
