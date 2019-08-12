package com.bank.service;

import java.math.BigDecimal;
import java.util.List;

import com.bank.domain.AccountType;
import com.bank.domain.Recipient;
import com.bank.domain.Transaction;
import com.bank.domain.User;

public interface TransactionService {

    List<Transaction> findTransactionList(String username, AccountType accountType);

    void saveTransaction(Transaction transaction);

    void betweenAccountsTransfer(String transferFrom, String transferTo, BigDecimal amount, User user) throws IllegalAccessException, Exception;

    List<Recipient> findRecipientList(String username);

    Recipient saveRecipient(Recipient recipient, String username) throws IllegalAccessException;

    Recipient findRecipientById(long id);

    void deleteRecipientById(long idLong);

    void localTransfer(User user, long recipientId, String accountType, BigDecimal amount) throws IllegalAccessException;
}
