package com.qintess.benefits.card.system.service;

import com.qintess.benefits.card.system.domain.User;
import com.qintess.benefits.card.system.domain.dto.CreateUserRequestDTO;

import java.util.List;

public interface UserService {
    User create(CreateUserRequestDTO user);
    List<User> list();
}
