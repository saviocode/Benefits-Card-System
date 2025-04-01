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
    private Long id;

    private String number;

    private Double debit;

    private Double credit;

    @Enumerated(EnumType.STRING)
    private CardType type;

    private LocalDate validity;

    private boolean active = Boolean.TRUE;

}


