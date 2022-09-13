package com.mindhub.homebanking;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

        @Autowired
        LoanRepository loanRepository;

        @Autowired
        ClientRepository clientRepository;

        @Autowired
        AccountRepository accountRepository;




        @Test
        public void existLoans(){

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans,is(not(empty())));

        }


        @Test
        public void existPersonalLoan(){

            List<Loan> loans = loanRepository.findAll();

            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

        }


        @Test
        public void existClient(){

            List<Client> client = clientRepository.findAll();

            assertThat(client,is(not(empty())));

        }


        @Test
        public void existClientPersonal(){

            List<Client> client = clientRepository.findAll();

            assertThat(client, hasItem(hasProperty("firstName", is("melba"))));
        }


        @Test
        public void existPropertyLocalDateTime(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, hasItem(hasProperty("number", startsWith("VIN"))));
        }


}
