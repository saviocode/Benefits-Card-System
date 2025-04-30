package com.qintess.benefits.card.system.domain.dto;

import com.qintess.benefits.card.system.domain.enums.RoleUser;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String userName;

    @NotBlank(message = "password é obrigatório")
    private String password;

    @NotBlank(message = "role é obrigatório")
    private RoleUser role = RoleUser.BENEFICIARIO;
}
