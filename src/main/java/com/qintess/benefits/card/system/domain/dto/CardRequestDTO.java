package com.qintess.benefits.card.system.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequestDTO {

    private String number;
    private Double credit;
    private Double debit;
    private LocalDate validity;
    private Boolean active;
}
