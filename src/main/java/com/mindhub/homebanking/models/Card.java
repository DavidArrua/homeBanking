package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    private String cardHolder;

    private String number;

    private Integer cvv;

    private LocalDateTime thruDate, fromDate;

    private CardType cardType;

    private CardColor cardColor;

    private boolean stateOfCard;

    public Card() {
    }

    public Card(Client client, String cardHolder, String number, Integer cvv, LocalDateTime thruDate, LocalDateTime fromDate, CardType cardType, CardColor cardColor, boolean stateOfCard) {
        this.client = client;
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.stateOfCard = stateOfCard;
    }

    public Card(Client client, String cardHolder, String number, Integer cvv, LocalDateTime thruDate, LocalDateTime fromDate, CardType cardType, CardColor cardColor, boolean stateOfCard, Account account) {
        this.client = client;
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.stateOfCard = stateOfCard;
        this.account = account;
    }



    public Long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public boolean isStateOfCard() {
        return stateOfCard;
    }

    public void setStateOfCard(boolean stateOfCard) {
        this.stateOfCard = stateOfCard;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
