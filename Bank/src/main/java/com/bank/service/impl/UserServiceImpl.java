package com.bank.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.dao.RoleDao;
import com.bank.dao.UserDao;
import com.bank.domain.Account;
import com.bank.domain.User;
import com.bank.domain.security.RoleName;
import com.bank.domain.security.UserRole;
import com.bank.service.AccountService;
import com.bank.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);

        return (user == null) ? null : user;
    }

    @Override
    public User findByEmail(String email) {
        User user = userDao.findByEmail(email);

        return (user == null) ? null : user;
    }

    @Override
    public User createUser(User user) {
        User localUser = userDao.findByUsername(user.getUsername());

        if (localUser != null) {
            LOG.info("User with Username {} already exists. Nothing will be done!", user.getUsername());
        } else {
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);


            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(user, roleDao.findByName(RoleName.ROLE_USER.toString())));

            for (UserRole ur : userRoles) {
                roleDao.save(ur.getRole());
            }

            user.setUserRoles(userRoles);


            Account currentAccount = accountService.createCurrentAccount();
            Account savingsAccount = accountService.createSavingsAccount();


            user.getAccounts().add(currentAccount);
            currentAccount.setUser(user);

            user.getAccounts().add(savingsAccount);
            savingsAccount.setUser(user);


            localUser = userDao.save(user);
        }

        return localUser;
    }

}
