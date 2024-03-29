package com.musicstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musicstore.dao.CustomerDao;
import com.musicstore.model.Customer;
import com.musicstore.service.CustomerService;

import java.util.List;

/**
 * Created by Le on 1/25/2016.
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public void addCustomer(Customer customer) {
        customerDao.addCustomer(customer);
    }

    public Customer getCustomerById(int customerId) {
        return customerDao.getCustomerById(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    public Customer getCustomerByUsername(String username) {
        return customerDao.getCustomerByUsername(username);
    }
}
