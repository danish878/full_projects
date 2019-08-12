package com.bank.service.impl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.dao.AccountDao;
import com.bank.domain.Account;
import com.bank.domain.AccountType;
import com.bank.domain.Transaction;
import com.bank.domain.TransactionStatus;
import com.bank.domain.TransactionType;
import com.bank.domain.User;
import com.bank.service.AccountService;
import com.bank.service.TransactionService;
import com.bank.service.UserService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Override
    public Account createCurrentAccount() {
        return createAccount(AccountType.CURRENT);
    }

    @Override
    public Account createSavingsAccount() {
        return createAccount(AccountType.SAVINGS);
    }

    private Account createAccount(AccountType accountType) {

        Account account = new Account();
        account.setType(accountType);
        account.setBalance(new BigDecimal(0.0));
        account.setNumber(accountGen());

        accountDao.save(account);

        return accountDao.findByNumber(account.getNumber());
    }

    @Override
    public void deposit(AccountType accountType, double amount, Principal principal) {

        User user = userService.findByUsername(principal.getName());

        if (accountType.equals(AccountType.CURRENT)) {
            Account currentAccount = getSpecificAccount(user.getAccounts(), AccountType.CURRENT);

            depositToAccount(currentAccount, amount);
        } else {
            Account savingsAccount = getSpecificAccount(user.getAccounts(), AccountType.SAVINGS);

            depositToAccount(savingsAccount, amount);
        }
    }

    @Override
    public void withdraw(AccountType accountType, double amount, Principal principal) throws Exception {

        User user = userService.findByUsername(principal.getName());

        if (accountType.equals(AccountType.CURRENT)) {
            Account currentAccount = getSpecificAccount(user.getAccounts(), AccountType.CURRENT);

            withdrawFromAccount(currentAccount, amount);
        } else {
            Account savingsAccount = getSpecificAccount(user.getAccounts(), AccountType.SAVINGS);

            withdrawFromAccount(savingsAccount, amount);
        }

    }

    private void withdrawFromAccount(Account account, double amount) throws Exception {

        if (account.getBalance().compareTo(new BigDecimal(amount)) == -1) { // second value (amount) is greater than first value (balance)
            throw new Exception("Insufficient Balance");
        }

        account.setBalance(account.getBalance().subtract(new BigDecimal(amount)));
        accountDao.save(account);


        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setDescription(String.format("Withdraw from %s Account", account.getType().toString().toLowerCase()));
        transaction.setType(TransactionType.WITHIN_ACCOUNT);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setAmount(amount);
        transaction.setAvailableBalance(account.getBalance());
        transaction.setAccount(account);

        transactionService.saveTransaction(transaction);

    }

    private void depositToAccount(Account account, double amount) {

        account.setBalance(account.getBalance().add(new BigDecimal(amount)));
        accountDao.save(account);


        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setDescription(String.format("Deposit to %s Account", account.getType().toString().toLowerCase()));
        transaction.setType(TransactionType.WITHIN_ACCOUNT);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setAmount(amount);
        transaction.setAvailableBalance(account.getBalance());
        transaction.setAccount(account);

        transactionService.saveTransaction(transaction);

    }

    public static Account getSpecificAccount(List<Account> accounts, AccountType type) {

        Account specificAccount = null;

        for (Account account : accounts) {
            if (account.getType().equals(type)) {
                specificAccount = account;
                break;
            }
        }

        return specificAccount;
    }

    private int accountGen() {
        return new Random().nextInt(99999999);
    }

}
