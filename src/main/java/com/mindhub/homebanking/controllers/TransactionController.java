package com.mindhub.homebanking.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mindhub.homebanking.dtos.PaymentsDTO;
import com.mindhub.homebanking.dtos.PdfDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Stream;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;


@RestController
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;


    @Transactional
    @RequestMapping(path = "/api/clients/current/transaction", method = RequestMethod.POST)
    public ResponseEntity<Object> newTransaction(@RequestParam Double amount, @RequestParam String description, @RequestParam String originAccount, @RequestParam String destinyAccount, Authentication authentication) {

                Client newClientAuthentication = clientService.getClientByEmail(authentication.getName());
                Account newOriginAccount = accountService.getAccountByNumber(originAccount);
                Account newDestinyAccount = accountService.getAccountByNumber(destinyAccount);

        if (newClientAuthentication == null){
            return new ResponseEntity<>("error", HttpStatus.FORBIDDEN);
        }
        if (amount <= 0 ||amount == null ) {
            return new ResponseEntity<>("Invalid amount", HttpStatus.FORBIDDEN);
        }
        if ( description.isEmpty() || originAccount.isEmpty() || destinyAccount.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (originAccount.equals(destinyAccount)) {
            return new ResponseEntity<>("Same account´s", HttpStatus.FORBIDDEN);
        }
        if (newOriginAccount == null) {
            return new ResponseEntity<>("Origin account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if (newDestinyAccount == null) {
            return new ResponseEntity<>("Destiny account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if(accountService.getAccountByNumber(originAccount).getBalance() < amount){
            return new ResponseEntity<>("Not enough money", HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction(newOriginAccount,DEBIT,-amount,description + " go to " + destinyAccount,LocalDateTime.now());
        Transaction creditTransaction = new Transaction(newDestinyAccount,CREDIT,amount,description + " from " + originAccount,LocalDateTime.now());
        transactionService.saveTransaction(debitTransaction);
        transactionService.saveTransaction(creditTransaction);

        newOriginAccount.setBalance(newOriginAccount.getBalance() - amount);
        newDestinyAccount.setBalance(newDestinyAccount.getBalance() + amount);

        accountService.saveAccount(newOriginAccount);
        accountService.saveAccount(newDestinyAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @PostMapping("api/pdf")
    public ResponseEntity<Object> newPDF(@RequestBody PdfDTO pdfDTO, Authentication authentication) throws DocumentException, FileNotFoundException {

        Account account = accountService.getAccountByNumber(pdfDTO.getAccount());
        Client client = clientService.getClientByEmail(authentication.getName());


        if (client == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (account == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (pdfDTO.getFrom()== null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (pdfDTO.getThru() == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        Set<Transaction> transactionsSearch = transactionService.filterTransactionsDate(pdfDTO.getFrom(), pdfDTO.getThru(), account);

        createPDF(transactionsSearch);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    public static void createPDF(Set<Transaction> transactions) throws DocumentException, FileNotFoundException {
    var doc = new Document();
        String route = System.getProperty("user.home");
        PdfWriter.getInstance(doc, new FileOutputStream("Your-transactions.pdf"));
        PdfWriter.getInstance(doc, new FileOutputStream(route + "/Desktop/TransactionInfo.pdf"));

    doc.open();

    var bold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    var paragraph = new Paragraph("details of your transactions");

    var table = new PdfPTable(4);
        Stream.of("Amount", "Description", "date", "Transaction type").forEach(table::addCell);

        transactions.forEach(transaction -> {
        table.addCell(transaction.getAmount().toString());
        table.addCell(transaction.getDescription());
        table.addCell(transaction.getDate().toString());
        table.addCell(transaction.getType().toString());
    });

        paragraph.add(table);
        doc.add(paragraph);
        doc.close();}


    @Transactional
    @PostMapping("/api/clients/current/transactions/payments")
    public ResponseEntity<Object> newPayments(Authentication authentication, @RequestBody PaymentsDTO paymentsDTO){
        Client client = clientService.getClientByEmail(authentication.getName());
        Card card = cardService.getCardById(paymentsDTO.getId());
        Account accountOrigin = card.getAccount();
        Card cardNumber = cardService.getCardByNumber(paymentsDTO.getNumber());

        if (client == null){
            return new ResponseEntity<>("client does not exist", HttpStatus.FORBIDDEN);
        }
        if (card == null){
            return new ResponseEntity<>("card does not exist", HttpStatus.FORBIDDEN);
        }
        if(!card.isStateOfCard()){
            return new ResponseEntity<>("disabled card", HttpStatus.FORBIDDEN);
        }
        if(cardNumber == null){
            return new ResponseEntity<>("card number does not exist", HttpStatus.FORBIDDEN);
        }

        if (paymentsDTO.getAmount() <= 0 ){
            return new ResponseEntity<>("invalid amount", HttpStatus.FORBIDDEN);
        }

        if(accountOrigin == null){
            return new ResponseEntity<>("account does not exist", HttpStatus.FORBIDDEN);
        }
        if (paymentsDTO.getAmount() > accountOrigin.getBalance()){
            return new ResponseEntity<>("Amount is invalid", HttpStatus.FORBIDDEN);
        }

        transactionService.saveTransaction(new Transaction(accountOrigin,TransactionType.DEBIT,- paymentsDTO.getAmount(), paymentsDTO.getDescription(),LocalDateTime.now()));
        accountOrigin.setBalance(accountOrigin.getBalance() - paymentsDTO.getAmount());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

