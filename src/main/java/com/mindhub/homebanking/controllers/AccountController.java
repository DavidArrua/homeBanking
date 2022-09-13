package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;


    @RequestMapping("/api/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAllAccounts().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    @RequestMapping("/api/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id){
        return new AccountDTO(accountService.getAccountById(id));
    }

    @RequestMapping(path = "/api/clients/current/accounts", method = RequestMethod.POST)
    public ResponseEntity<Object> createNewCurrentClientAccount(
                Authentication authentication,
                @RequestParam String accountType
                ){

            String randomNumber = "VIN" + getRandomNumber(0, 99999999);
            Client client = clientService.getClientByEmail(authentication.getName());

            if(client.getAccounts().stream().filter(account -> account.isAccountState() == true).count() >= 3){
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            accountService.saveAccount(new Account(randomNumber, LocalDateTime.now(), 0.0, client, true, AccountType.valueOf(accountType)));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    @PatchMapping("/api/clients/current/accounts/state")
    public ResponseEntity<Object> changeStateAccount(
            Authentication authentication,
            @RequestParam String number
    ){
        Client client = clientService.getClientByEmail(authentication.getName());
        Account accountNumber = accountService.getAccountByNumber(number);


        if(number.isEmpty()){
            return new ResponseEntity<>("missing data", HttpStatus.FORBIDDEN);
        }

        if (client == null){
            return new ResponseEntity<>("missing data", HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().contains(accountNumber)){
            return new ResponseEntity<>("this account does not exist", HttpStatus.FORBIDDEN);
        }

        if (accountNumber.getBalance() > 0){
            return new ResponseEntity<>("you can't delete an account with money", HttpStatus.FORBIDDEN);
        }


        accountNumber.setAccountState(false);
        accountService.saveAccount(accountNumber);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
