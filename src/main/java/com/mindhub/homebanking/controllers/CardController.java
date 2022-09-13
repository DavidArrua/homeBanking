package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;

import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
public class CardController {

    @Autowired
    ClientService clientService;

    @Autowired
    CardService cardService;


    @Autowired
    AccountService accountService;


    @RequestMapping(path = "/api/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> NewCard(
            @RequestParam CardType cardType,
            @RequestParam CardColor cardColor,
            @RequestParam String numberAccount,
            Authentication authentication
            ){
        Account account = accountService.getAccountByNumber(numberAccount);

        String cardNumber = CardUtils.getCardNumber();
        int cardCvv = CardUtils.getCardCvv();

        Client client = clientService.getClientByEmail(authentication.getName());

        if(cardType == null || cardColor == null) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(client.getCards().stream().filter(card -> card.getCardType().equals(cardType) && card.isStateOfCard() == true).count() >= 3 ){
            return new ResponseEntity<>("you already have the maximum of this type", HttpStatus.FORBIDDEN);
        }

        if(client.getCards().stream().anyMatch(card -> card.getCardColor().equals(cardColor) && card.getCardType().equals(cardType) && card.isStateOfCard())){
            return new ResponseEntity<>("for each card you can only select one color", HttpStatus.FORBIDDEN);         }

        if(accountService.getAccountByNumber(account.getNumber()) == null ){
            return new ResponseEntity<>("this account does not exist", HttpStatus.FORBIDDEN);
        }


        cardService.saveCard(new Card(client, client.toString(), cardNumber, cardCvv, LocalDateTime.now(), LocalDateTime.now().plusYears(5), cardType, cardColor, true, account));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PatchMapping("/api/clients/current/cards/state")
    public  ResponseEntity<Object> changeStateCard(
            @RequestParam String number,
            Authentication authentication
    ){
        Client client = clientService.getClientByEmail(authentication.getName());
        Card cardNumber = cardService.getCardByNumber(number);

        if (client == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (cardNumber == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(number.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(!client.getCards().contains(cardNumber)){

            return new ResponseEntity<>("the card does not exist", HttpStatus.FORBIDDEN);
        }

        cardNumber.setStateOfCard(false);
        cardService.saveCard(cardNumber);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }




}
