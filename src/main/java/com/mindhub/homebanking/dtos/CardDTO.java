package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;


import java.time.LocalDateTime;

public class CardDTO {

    private long id;

    private String cardHolder;

    private String number;

    private Integer cvv;

    private LocalDateTime thruDate, fromDate;

    private CardType cardType;

    private CardColor cardColor;

    private boolean stateOfCard;

    public CardDTO() {
    }

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
        this.thruDate = card.getThruDate();
        this.fromDate = card.getFromDate();
        this.cardType = card.getCardType();
        this.cardColor = card.getCardColor();
        this.stateOfCard = card.isStateOfCard();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public boolean isStateOfCard() {
        return stateOfCard;
    }
}
