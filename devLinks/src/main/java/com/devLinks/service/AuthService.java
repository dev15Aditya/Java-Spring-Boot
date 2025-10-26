package com.devLinks.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devLinks.config.JwtUtil;
import com.devLinks.dto.AuthRequest;
import com.devLinks.model.User;
import com.devLinks.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String signup(AuthRequest request){
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already existes.");
        }

        User user = User.builder()
                    .email(request.getEmail())
                    .name(request.getName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role("USER")
                    .build();

        userRepository.save(user);
        return jwtUtil.generateToken(user.getEmail());
    }

    public String login(AuthRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User login"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail());
    }
}
