package com.bank.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.dao.AccountDao;
import com.bank.dao.RecipientDao;
import com.bank.dao.TransactionDao;
import com.bank.domain.Account;
import com.bank.domain.AccountType;
import com.bank.domain.Recipient;
import com.bank.domain.Transaction;
import com.bank.domain.TransactionStatus;
import com.bank.domain.TransactionType;
import com.bank.domain.User;
import com.bank.service.TransactionService;
import com.bank.service.UserService;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private RecipientDao recipientDao;


    @Override
    public List<Transaction> findTransactionList(String username, AccountType accountType) {

        User user = userService.findByUsername(username);

        Account currentAccount =
                AccountServiceImpl.getSpecificAccount(user.getAccounts(), accountType);

        return getSpecificTransactionList(currentAccount.getTransactionList(),
                accountType);
    }


    @Override
    public void saveTransaction(Transaction transaction) {

        transactionDao.save(transaction);
    }

    @Override
    public void betweenAccountsTransfer(String transferFrom, String transferTo, BigDecimal amount, User user) throws IllegalAccessException, Exception {

        Account currentAccount = AccountServiceImpl.getSpecificAccount(user.getAccounts(), AccountType.CURRENT);
        Account savingsAccount = AccountServiceImpl.getSpecificAccount(user.getAccounts(), AccountType.SAVINGS);

        if (transferFrom.equalsIgnoreCase(AccountType.CURRENT.toString())
                && transferTo.equalsIgnoreCase(AccountType.SAVINGS.toString())) {

            if (!isSufficientBalance(currentAccount, amount)) {
                throw new IllegalAccessException("Insufficient Balance");
            }

            currentAccount.setBalance(currentAccount.getBalance().subtract(amount));
            savingsAccount.setBalance(savingsAccount.getBalance().add(amount));

            accountDao.save(currentAccount);
            accountDao.save(savingsAccount);


            Transaction transaction = new Transaction();
            transaction.setDate(new Date());
            transaction.setDescription(String.format("Between account transfer from %s to %s", transferFrom, transferTo));
            transaction.setType(TransactionType.BETWEEN_ACCOUNT);
            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction.setAmount(amount.doubleValue());
            transaction.setAvailableBalance(currentAccount.getBalance());
            transaction.setAccount(currentAccount);

            transactionDao.save(transaction);
        } else if (transferFrom.equalsIgnoreCase(AccountType.SAVINGS.toString())
                && transferTo.equalsIgnoreCase(AccountType.CURRENT.toString())) {

            if (!isSufficientBalance(savingsAccount, amount)) {
                throw new IllegalAccessException("Insufficient Balance");
            }

            savingsAccount.setBalance(savingsAccount.getBalance().subtract(amount));
            currentAccount.setBalance(currentAccount.getBalance().add(amount));

            accountDao.save(savingsAccount);
            accountDao.save(currentAccount);


            Transaction transaction = new Transaction();
            transaction.setDate(new Date());
            transaction.setDescription(String.format("Between account transfer from %s to %s", transferFrom, transferTo));
            transaction.setType(TransactionType.BETWEEN_ACCOUNT);
            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction.setAmount(amount.doubleValue());
            transaction.setAvailableBalance(savingsAccount.getBalance());
            transaction.setAccount(savingsAccount);

            transactionDao.save(transaction);
        } else {
            throw new Exception("Invalid Transfer");
        }

    }

    @Override
    public List<Recipient> findRecipientList(String username) {

        List<Recipient> recipientList = (List<Recipient>) recipientDao.findAll();

        recipientList = recipientList
                .stream()
                .filter(recipient -> username.equals(recipient.getUser().getUsername()))
                .collect(Collectors.toList());

        return recipientList;
    }

    @Override
    public Recipient saveRecipient(Recipient recipient, String username) throws IllegalAccessException {

        if (!recipientExists(recipient)) {
            throw new IllegalAccessException("Recipient Account does not exist!!!");
        }

        User user = userService.findByUsername(username);
        recipient.setUser(user);

        return recipientDao.save(recipient);
    }

    @Override
    public Recipient findRecipientById(long id) {
        return recipientDao.findById(id).get();
    }

    @Override
    public void deleteRecipientById(long id) {
        recipientDao.deleteById(id);
    }

    @Override
    public void localTransfer(User user, long recipientId, String accountType, BigDecimal amount) throws IllegalAccessException {

        if (accountType.equalsIgnoreCase(AccountType.CURRENT.toString())) {

            Account currentAccount = AccountServiceImpl.getSpecificAccount(user.getAccounts(), AccountType.CURRENT);

            if (!isSufficientBalance(currentAccount, amount)) {
                throw new IllegalAccessException("Insufficient Balance");
            }

            currentAccount.setBalance(currentAccount.getBalance().subtract(amount));
            accountDao.save(currentAccount);

            Recipient recipient = recipientDao.findById(recipientId).get();
            transferToRecipient(recipient, amount);

            Transaction transaction = new Transaction();
            transaction.setDate(new Date());
            transaction.setDescription(String.format("Transfer to recipient %s", recipient.getName()));
            transaction.setType(TransactionType.LOCAL_TRANSFER);
            transaction.setStatus(TransactionStatus.COMPLETED);
            transaction.setAmount(amount.doubleValue());
            transaction.setAvailableBalance(currentAccount.getBalance());
            transaction.setAccount(currentAccount);

            transactionDao.save(transaction);
        }

    }

    private boolean recipientExists(Recipient recipient) {

        Account account = accountDao.findByNumber(recipient.getAccountNumber());

        if (account != null)
            return true;

        return false;
    }

    private void transferToRecipient(Recipient recipient, BigDecimal amount) {

        Account recipientAccount = accountDao.findByNumber(recipient.getAccountNumber());

        recipientAccount.setBalance(recipientAccount.getBalance().add(amount));
    }


    public static boolean isSufficientBalance(Account account, BigDecimal amount) {

        if (account.getBalance().compareTo(amount) == -1) { // second value (amount) is greater than first value (balance)
            return false;
        }

        return true;
    }


    public static List<Transaction> getSpecificTransactionList(List<Transaction> transactionList, AccountType accountType) {

        List<Transaction> specificTransactionList = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            if (transaction.getAccount().getType().equals(accountType))
                specificTransactionList.add(transaction);
        }

        return specificTransactionList;

    }


}
