package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Card;

public interface CardService {

    public Card saveCard(Card card);

    public Card getCardByNumber(String number);

    public Card getCardById (Long id);
}
