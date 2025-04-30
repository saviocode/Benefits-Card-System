package com.qintess.benefits.card.system.controller;


import com.qintess.benefits.card.system.config.JwtUtil;
import com.qintess.benefits.card.system.domain.dto.JwtResponseDTO;
import com.qintess.benefits.card.system.domain.dto.LoginRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserName(), loginRequest.getPassword())
        );
        String token = jwtUtil.generateJwtToken(authentication.getName());

        return ResponseEntity.ok(new JwtResponseDTO(token));
    }
}
