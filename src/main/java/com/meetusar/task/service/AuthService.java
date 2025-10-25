package com.meetusar.task.service;

import com.meetusar.task.config.JwtFilter;
import com.meetusar.task.dto.AuthRequest;
import com.meetusar.task.dto.AuthResponse;
import com.meetusar.task.dto.RegisterRequest;
import com.meetusar.task.entity.User;
import com.meetusar.task.repository.UserRepository;
import com.meetusar.task.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements IAuthService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public void register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = new User(req.getEmail(), passwordEncoder.encode(req.getPassword()), req.getName());
        userRepository.save(user);
    }

    @Override
    public AuthResponse login(AuthRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponse(token);
    }

    @Override
    public void logout(String token) {
        JwtFilter.blacklistToken(token);
    }
}
