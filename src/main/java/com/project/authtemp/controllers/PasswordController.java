package com.project.authtemp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.authtemp.payload.request.ChangePasswrodRequest;
import com.project.authtemp.payload.request.ForgotPasswordRequest;
import com.project.authtemp.payload.response.GeneralMessageResponse;
import com.project.authtemp.services.ChangePasswordService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/password")
@RequiredArgsConstructor
public class PasswordController {

    private final ChangePasswordService service;

    @RequestMapping("/forgot-password") // TODO fix body issues
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
