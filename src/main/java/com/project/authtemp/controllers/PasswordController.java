package com.project.authtemp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.authtemp.payload.request.ChangePasswrodRequest;
import com.project.authtemp.payload.request.ForgotPasswordRequest;
import com.project.authtemp.payload.response.GeneralMessageResponse;
import com.project.authtemp.services.ChangePasswordService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/password")
@RequiredArgsConstructor
public class PasswordController {

    private final ChangePasswordService service;

    @PostMapping("/forgot-password")
    public ResponseEntity<GeneralMessageResponse> sendforgotPasswordemail(@RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(service.sendForgotPasswordMail(request));
    }

    @PostMapping("/changepasswordWithPassword")
    public ResponseEntity<GeneralMessageResponse> changePasswordWithPassword(
            @RequestBody ChangePasswrodRequest request) {
        return ResponseEntity.ok(service.changePasswordwithPassword(request));
    }

    @PostMapping("/changepasswordWithToken")
    public ResponseEntity<GeneralMessageResponse> changePasswordWithToken(
            @RequestParam String token, @RequestBody String newPassword) {
        return ResponseEntity.ok(service.changePasswordWithToken(token, newPassword));
    }

}
