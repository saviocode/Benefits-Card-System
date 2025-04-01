package com.qintess.benefits.card.system.service.impl;

import com.qintess.benefits.card.system.domain.Card;
import com.qintess.benefits.card.system.repository.CardRepository;
import com.qintess.benefits.card.system.service.CardService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {
    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public Card create(Card card) {
        Card novoCartao = Card.builder()
                .number(card.getNumber())
                .credit(card.getCredit() != null ? card.getCredit() : 0.0)
                .debit(card.getDebit() != null ? card.getDebit() : 0.0)
                .validity(card.getValidity())
                .type(card.getType())
                .active(card.isActive())
                .build();
        return cardRepository.save(novoCartao);
    }

    @Override
    @Transactional
    public Optional<Card> credit(Long id, Double valor) {
        return cardRepository.findById(id).map(card -> {
            if (!card.isActive()) {
                throw new IllegalStateException("Cartão inativo, não é possível creditar.");
            }
            Double creditoAtual = card.getCredit() != null ? card.getCredit() : 0.0;
            card.setCredit(creditoAtual + valor);
            return cardRepository.save(card);
        });
    }

    @Override
    @Transactional
    public Optional<Card> debit(Long id, Double valor) {
        return cardRepository.findById(id).map(card -> {
            if (!card.isActive()) {
                throw new IllegalStateException("Cartão inativo, não é possível debitar.");
            }
            Double saldoAtual = balance(card);
            if (saldoAtual < valor) {
                throw new IllegalArgumentException("Saldo insuficiente para debitar o valor solicitado.");
            }
            Double debitoAtual = card.getDebit() != null ? card.getDebit() : 0.0;
            card.setDebit(debitoAtual + valor);
            return cardRepository.save(card);
        });
    }

    @Override
    public Optional<Card> findById(Long id) {
        return cardRepository.findById(id);
    }

    @Override
    public Double balance(Card card) {
        double credito = card.getCredit() != null ? card.getCredit() : 0.0;
        double debito = card.getDebit() != null ? card.getDebit() : 0.0;
        return credito - debito;
    }

    @Override
    @Transactional
    public Optional<Card> disabled(Long id) {
        int updatedRows = cardRepository.disabled(id);
        if (updatedRows > 0) {
            return cardRepository.findById(id);
        }
        return Optional.empty();
    }



}
