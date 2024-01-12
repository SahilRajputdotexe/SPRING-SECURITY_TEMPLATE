package com.project.authtemp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.authtemp.payload.request.ChangePasswrodRequest;
import com.project.authtemp.payload.request.ForgotPasswordRequest;
import com.project.authtemp.payload.response.GeneralMessageResponse;
import com.project.authtemp.services.ChangePasswordService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RequestMapping("api/v1/password")
public class PasswordController {

    ChangePasswordService service;

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
    public String changePasswordWithToken() {
        return "POST:: reset password with token";
    }

}
