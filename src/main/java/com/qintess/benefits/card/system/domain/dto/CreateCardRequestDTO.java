package com.qintess.benefits.card.system.domain.dto;

import com.qintess.benefits.card.system.domain.enums.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardRequestDTO {

    @NotBlank(message = "O número do cartão é obrigatório")
    private String number;

    @NotNull(message = "O tipo do cartão é obrigatório")
    private Double credit;

    private Double debit;

    private LocalDate validity;

    private Boolean active;

    private CardType type;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long userId;
}
