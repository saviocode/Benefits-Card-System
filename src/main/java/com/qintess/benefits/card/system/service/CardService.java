package com.qintess.benefits.card.system.service;

import com.qintess.benefits.card.system.domain.Card;
import com.qintess.benefits.card.system.repository.CardRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {
    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Transactional
    public Card create(Card card) {
        logger.info("Criando novo cartão com número: {}", card.getNumber());
        card.setActive(Boolean.TRUE);
        return cardRepository.save(card);
    }

    @Transactional
    public Optional<Card> credit(Long id, Double valor) {
        logger.info("Creditando valor {} no cartão com id: {}", valor, id);
        return cardRepository.findById(id).map(card -> {
            if (!card.isActive()) {
                throw new IllegalStateException("Cartão inativo, não é possível creditar.");
            }
            Double creditoAtual = card.getCredit() != null ? card.getCredit() : 0.0;
            card.setCredit(creditoAtual + valor);
            return cardRepository.save(card);
        });
    }

    @Transactional
    public Optional<Card> debit(Long id, Double valor) {
        logger.info("Debitando valor {} no cartão com id: {}", valor, id);
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

    public Double balance(Card card) {
        double credito = card.getCredit() != null ? card.getCredit() : 0.0;
        double debito = card.getDebit() != null ? card.getDebit() : 0.0;
        return credito - debito;
    }

    @Transactional
    public Optional<Card> disabled(Long id) {
        int updatedRows = cardRepository.disabled(id);
        if (updatedRows > 0) {
            return cardRepository.findById(id);
        }
        return Optional.empty();
    }

}
