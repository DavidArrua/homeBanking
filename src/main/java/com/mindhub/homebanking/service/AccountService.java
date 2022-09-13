package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;


public interface AccountService {

    public Account saveAccount (Account account);

    public List<Account> getAllAccounts ();

    public Account getAccountById(long id);

    public Account getAccountByNumber (String number);
}
