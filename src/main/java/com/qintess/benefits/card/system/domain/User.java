package com.qintess.benefits.card.system.domain;


import com.qintess.benefits.card.system.domain.enums.RoleUser;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_user")
    private RoleUser role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Card card;
}
