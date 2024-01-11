package com.project.authtemp.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.authtemp.model.token.TokenRepository;
import com.project.authtemp.model.user.UserRepository;
import com.project.authtemp.payload.request.ChangePasswrodRequest;
import com.project.authtemp.payload.response.ErrorResponse;
import com.project.authtemp.payload.response.GeneralMessageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    public GeneralMessageResponse changePasswordwithPassword(ChangePasswrodRequest request) {

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return GeneralMessageResponse.builder()
                    .isSuccess(false)
                    .message("Wrong password")
                    .errorObject(new ErrorResponse(400, "Wrong password", "Wrong password"))
                    .build();
        } else {
            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                return GeneralMessageResponse.builder()
                        .isSuccess(false)
                        .message("Password are not the same")
                        .errorObject(new ErrorResponse(400, "Password are not the same", "Password are not the same"))
                        .build();
            } else {
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                repository.save(user);
                return GeneralMessageResponse.builder()
                        .isSuccess(true)
                        .message("Password changed successfully")
                        .build();
            }
        }

    }
}
