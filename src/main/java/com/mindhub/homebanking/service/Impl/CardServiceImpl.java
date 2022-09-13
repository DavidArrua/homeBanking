package com.mindhub.homebanking.service.Impl;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card getCardByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public Card getCardById(Long id) {
        return cardRepository.findById(id).get();
    }
}
