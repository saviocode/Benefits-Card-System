package com.qintess.benefits.card.system.service.impl;

import com.qintess.benefits.card.system.domain.User;
import com.qintess.benefits.card.system.domain.dto.CreateUserRequestDTO;
import com.qintess.benefits.card.system.repository.UserRepository;
import com.qintess.benefits.card.system.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User create(CreateUserRequestDTO user) {
        User users = User.builder()
                .userName(user.getUserName())
                .password(passwordEncoder.encode(user.getPassword()))
                .role(user.getRole())
                .build();
        return userRepository.save(users);
    }

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }
}
