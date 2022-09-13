package com.mindhub.homebanking.service.Impl;


import com.mindhub.homebanking.models.Account;

import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number);
    }
}
