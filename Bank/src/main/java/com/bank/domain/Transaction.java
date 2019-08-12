package com.bank.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private Date date;
    private String description;
    private TransactionType type;
    private TransactionStatus status;
    private double amount;
    private BigDecimal availableBalance;

    @ManyToOne
    private Account account;

}
