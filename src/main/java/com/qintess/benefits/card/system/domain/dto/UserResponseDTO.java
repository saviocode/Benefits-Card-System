package com.qintess.benefits.card.system.domain.dto;

import com.qintess.benefits.card.system.domain.enums.RoleUser;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private String user_name;
    private String password;
    private RoleUser role = RoleUser.BENEFICIARIO;
}
