package com.qintess.benefits.card.system.controller;

import com.qintess.benefits.card.system.domain.Card;
import com.qintess.benefits.card.system.domain.dto.OperatorRequestDTO;
import com.qintess.benefits.card.system.service.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }


    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card) {
        logger.info("Creating new card with number: {}", card.getNumber());
        Card createdCard = cardService.create(card);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> getCard(@PathVariable Long id) {
        Optional<Card> optionalCard = cardService.findById(id);
        return optionalCard.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Long id) {
        Optional<Card> optionalCard = cardService.findById(id);
        if (optionalCard.isPresent()) {
            Double balance = cardService.balance(optionalCard.get());
            return ResponseEntity.ok(balance);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<Card> disableCard(@PathVariable Long id) {
        Optional<Card> disabledCard = cardService.disabled(id);
        return disabledCard.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/credit")
    public ResponseEntity<Card> creditCard(@PathVariable Long id, @RequestBody OperatorRequestDTO request) {
        Optional<Card> creditedCard = cardService.credit(id, request.getValue());
        return creditedCard.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/debit")
    public ResponseEntity<Card> debitCard(@PathVariable Long id, @RequestBody OperatorRequestDTO request) {
        try {
            Optional<Card> debitedCard = cardService.debit(id, request.getValue());
            return debitedCard.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            logger.error("Error during debit operation: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
