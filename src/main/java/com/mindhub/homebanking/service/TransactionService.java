package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.Set;

public interface TransactionService {

    public Transaction saveTransaction(Transaction transaction);


    public Set<Transaction> filterTransactionsDate(LocalDateTime from, LocalDateTime thru, Account account);
}
