package com.project.authtemp.services;

import org.json.JSONObject;

import com.project.authtemp.model.role.TokenRole;
import com.project.authtemp.model.token.Token;
import com.project.authtemp.model.token.TokenRepository;
import com.project.authtemp.model.token.TokenType;
import com.project.authtemp.model.user.User;
import com.project.authtemp.model.user.UserRepository;
import com.project.authtemp.payload.request.AuthenticationRequest;
import com.project.authtemp.payload.request.RegisterRequest;
import com.project.authtemp.payload.response.AuthenticationResponse;
import com.project.authtemp.payload.response.GeneralMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public GeneralMessageResponse register(RegisterRequest request) {
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    jwtService.saveAccessToken(savedUser, jwtToken);
    jwtService.saveRefreshToken(savedUser, refreshToken);
    JSONObject registerResponse = new JSONObject(
        AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build());

    return GeneralMessageResponse.builder()
        .isSuccess(true)
        .message("User registered successfully")
        .responseObject(registerResponse.toMap())
        .errorObject(null)
        .build();
  }

  public GeneralMessageResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()));
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    jwtService.saveAccessToken(user, jwtToken);
    jwtService.saveRefreshToken(user, refreshToken);
    JSONObject authResponse = new JSONObject(AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build());

    return GeneralMessageResponse.builder()
        .isSuccess(true)
        .message("User authenticated successfully")
        .responseObject(authResponse.toMap())
        .errorObject(null)
        .build();
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
          .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        jwtService.saveAccessToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
