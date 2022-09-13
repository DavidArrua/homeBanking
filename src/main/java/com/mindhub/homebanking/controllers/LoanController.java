package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class LoanController {

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanService loanService;

    @Transactional
    @RequestMapping(path="/api/client/loans", method= RequestMethod.POST)
    public ResponseEntity<Object> addLoan(@RequestBody LoanAplicationDTO loanAplicationDTO, Authentication authentication) {

        Client currentClientAuthentication = clientService.getClientByEmail(authentication.getName());
        Loan loanClientAuthentication = loanService.getLoanById(loanAplicationDTO.getId());
        Account accountLoan = accountService.getAccountByNumber(loanAplicationDTO.getAccountDestination());

        if ( loanAplicationDTO.getAmount() <= 0 || loanAplicationDTO.getPayments() <= 0 || loanAplicationDTO.getAccountDestination().isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }

        if (accountLoan == null) {

            return new ResponseEntity<>("account dosen't exist", HttpStatus.FORBIDDEN);

        }

        if (loanAplicationDTO.getAmount() > loanClientAuthentication.getMaxAmount()) {

            return new ResponseEntity<>("Amount limit exceeded", HttpStatus.FORBIDDEN);

        }

        if (!loanClientAuthentication.getPayments().contains(loanAplicationDTO.getPayments())) {

            return new ResponseEntity<>("Payments dosent allowed", HttpStatus.FORBIDDEN);

        }

        if (accountService.getAccountByNumber(loanAplicationDTO.getAccountDestination()) == null) {

            return new ResponseEntity<>("destiny account dosen't exist", HttpStatus.FORBIDDEN);

        }

        if (!currentClientAuthentication.getAccounts().contains(accountService.getAccountByNumber(accountLoan.getNumber()))) {

            return new ResponseEntity<>("Destiny account dosent match with client user", HttpStatus.FORBIDDEN);

        }

        Transaction transactionLoan = new Transaction(accountLoan,TransactionType.CREDIT,loanAplicationDTO.getAmount(),loanClientAuthentication.getName() + " Loan approved",LocalDateTime.now());
        double interestPlus = (loanAplicationDTO.getAmount())*0.2 + loanAplicationDTO.getAmount();

        transactionService.saveTransaction(transactionLoan);
        accountLoan.setBalance(accountLoan.getBalance()+loanAplicationDTO.getAmount());

        ClientLoan clientLoan = new ClientLoan(interestPlus,loanAplicationDTO.getPayments(),currentClientAuthentication,loanClientAuthentication);

        clientLoanService.saveClientLoan(clientLoan);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping("/api/loans")
    public List<LoanDTO> getloans()
    {return loanService.getAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());}

    @PostMapping("/api/admin/loans")
    public ResponseEntity<String> addAdminLoan (@RequestParam String name,@RequestParam double maxAmount, @RequestParam List<Integer> payments, Authentication authentication){

        Client adminAuthentication = clientService.getClientByEmail(authentication.getName());

        if(adminAuthentication == null){
            return new ResponseEntity<>("missing admin authentication", HttpStatus.FORBIDDEN);
        }

        if(name.isEmpty()){
            return new ResponseEntity<>("missing name of loan", HttpStatus.FORBIDDEN);
        }

        if(maxAmount <= 0){
            return new ResponseEntity<>("missing max amount of loan", HttpStatus.FORBIDDEN);
        }

        if(loanService.getAllLoans().stream().map(x -> x.getName()).collect(Collectors.toSet()).contains(name)){
            return new ResponseEntity<>("same name of previous loan", HttpStatus.FORBIDDEN);
        }


        loanService.saveLoan(new Loan(name, maxAmount,payments));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

