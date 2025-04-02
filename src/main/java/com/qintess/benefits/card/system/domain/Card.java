package com.qintess.benefits.card.system.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "card")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    @Column(name = "number", unique = true)
    private String number;

    @Column(name = "credit")
    private Double credit;

    @Column(name = "debit")
    private Double debit;

    @Column(name = "validity")
    private LocalDate validity;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType type;

    @Column(name = "active")
    private boolean active = Boolean.TRUE;

}


