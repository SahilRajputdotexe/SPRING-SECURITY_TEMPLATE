package com.project.authtemp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.authtemp.payload.request.AuthenticationRequest;
import com.project.authtemp.payload.request.RegisterRequest;
import com.project.authtemp.payload.response.ErrorResponse;
import com.project.authtemp.payload.response.GeneralMessageResponse;
import com.project.authtemp.services.AuthenticationService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<GeneralMessageResponse> register(
      @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<GeneralMessageResponse> authenticate(
      @RequestBody AuthenticationRequest request) {
    System.out.println(request.getEmail() + " " + request.getPassword());
    return ResponseEntity.ok(service.authenticate(request));

  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    service.refreshToken(request, response);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<GeneralMessageResponse> handleAuthenticationException(BadCredentialsException ex) {
    if (ex.getMessage().equals("Bad credentials")) {
      return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(GeneralMessageResponse.builder()
          .isSuccess(false)
          .message("Bad credentials")
          .responseObject(null)
          .errorObject(ErrorResponse.builder()
              .errorCode(HttpStatus.UNAUTHORIZED.value())
              .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
              .message("Incorrect password")
              .build())
          .build());
    }

    return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(GeneralMessageResponse.builder()
        .isSuccess(false)
        .message("Bad credentials")
        .responseObject(null)
        .errorObject(ErrorResponse.builder()
            .errorCode(HttpStatus.UNAUTHORIZED.value())
            .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
            .message(ex.getMessage())
            .build())
        .build());
  }
}
