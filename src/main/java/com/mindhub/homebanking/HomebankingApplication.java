package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			Client client1 = new Client("melba", "morel", "melba@mindhub.com", passwordEncoder.encode("123"));
			Account account1 = new Account("VIN001", LocalDateTime.now(), 7500.0,true, AccountType.CURRENT);
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 5000.0, client1,true, AccountType.CURRENT);
			Transaction transaction1 = new Transaction(account1, TransactionType.CREDIT, 500.00, "hola",LocalDateTime.now());
			Transaction transaction2 = new Transaction(account1, TransactionType.DEBIT, -20.00, "hola2",LocalDateTime.now());

			Loan loan1 = new Loan("Mortgage", 500000.00, List.of(12, 24, 36, 48, 60));
			Loan loan2 = new Loan("Personal", 100000.00, List.of(6,12,24));
			Loan loan3 = new Loan("Automotive", 300000.00, List.of(6,12,24,36));

			ClientLoan clientLoan1 = new ClientLoan(40000.00, 60, client1, loan1);
			ClientLoan clientLoan2 = new ClientLoan( 50000.00, 12, client1, loan2);
			Card card1 = new Card (client1, client1.toString(), "7745 6645 4563 8900", 356, LocalDateTime.now(), LocalDateTime.now().plusYears(5), CardType.DEBIT, CardColor.GOLD, true, account1);
			Card card2 = new Card (client1, client1.toString(),"4564 5645 6432 7654", 999,  LocalDateTime.now(), LocalDateTime.now(), CardType.CREDIT, CardColor.TITANIUM, true, account2);

			Client client2 = new Client("melboppp", "morel", "admin@mindhub.com", passwordEncoder.encode("123"));
			Account account4 = new Account("VIN003", LocalDateTime.now().plusDays(1), 500.0, client2,true, AccountType.CURRENT);
			ClientLoan clientLoan3 = new ClientLoan( 10000.00,  24, client2, loan2);
			ClientLoan clientLoan4 = new ClientLoan(  20000.00, 36, client2, loan3);
			Card card3 = new Card (client2, client2.toString(),"3232 3434 5678 9000", 111,  LocalDateTime.now(), LocalDateTime.now().plusYears(5), CardType.CREDIT, CardColor.SILVER, true);

			client1.addAccount(account1);

			clientRepository.save(client1);
			clientRepository.save(client2);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
		};
	}
	@Autowired
	private PasswordEncoder passwordEncoder;

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
}
