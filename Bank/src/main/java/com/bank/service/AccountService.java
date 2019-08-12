package com.bank.service;

import java.security.Principal;

import com.bank.domain.Account;
import com.bank.domain.AccountType;

public interface AccountService {

    Account createCurrentAccount();

    Account createSavingsAccount();

    void deposit(AccountType accountType, double amount, Principal principal);

    void withdraw(AccountType accountType, double amount, Principal principal) throws Exception;

}
