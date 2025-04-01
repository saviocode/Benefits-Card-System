package com.qintess.benefits.card.system.service;

import com.qintess.benefits.card.system.domain.Card;

import java.util.Optional;

public interface CardService {
    Card create(Card card);

    Double balance(Card card);

    Optional<Card> disabled(Long id);

    Optional<Card> credit(Long id, Double valor);

    Optional<Card> debit(Long id, Double valor);

    Optional<Card> findById(Long id);
}
