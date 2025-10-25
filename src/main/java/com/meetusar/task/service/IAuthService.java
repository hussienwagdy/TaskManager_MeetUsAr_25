package com.meetusar.task.service;

import com.meetusar.task.dto.AuthRequest;
import com.meetusar.task.dto.AuthResponse;
import com.meetusar.task.dto.RegisterRequest;

public interface IAuthService {
    void register(RegisterRequest req);
    AuthResponse login(AuthRequest req);
    void logout(String token);
}
