package com.ilham.service;

import com.ilham.models.User;
import com.ilham.request.LoginRequest;
import com.ilham.response.AuthResponse;

public interface AuthService {

    public AuthResponse signUp(User user);

    public AuthResponse signIn(LoginRequest loginRequest);
}
