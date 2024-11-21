package com.project.authtemp.services;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.authtemp.model.credentials.Credentials;
import com.project.authtemp.model.credentials.CredentialsRepository;
import com.project.authtemp.model.user.User;
import com.project.authtemp.model.user.UserRepository;
import com.project.authtemp.payload.request.SaveNewASRequest;
import com.project.authtemp.payload.response.ErrorResponse;
import com.project.authtemp.payload.response.GeneralMessageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CredentialsService {

    private final UserRepository repository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public GeneralMessageResponse createCredential(SaveNewASRequest request) {

        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        var accountShared = Credentials.builder().username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword())).websiteUrl(request.getWebsiteUrl()).user(user)
                .build();
        credentialsRepository.save(accountShared);// TODO: check if already exists and make a response type also fix
                                                  // naming scheme.
        return GeneralMessageResponse.builder().isSuccess(true).message("Account Shared created successfully").build();
    }

    public void shareCredential(Credentials credentials, User userToShareWith) {
        credentials.getSharedWithUsers().add(userToShareWith);
        credentials.setSharedAt(LocalDateTime.now()); // Optionally track when the credential was shared
        // Save the updated credentials object to the database
        credentialsRepository.save(credentials);
    }

    // public GeneralMessageResponse deleteAccountShared(Long id) {
    // accountSharedRepository.deleteById(id);
    // return GeneralMessageResponse.builder().isSuccess(true).message("Account
    // Shared deleted successfully").build();
    // }

    // public GeneralMessageResponse updateAccountShared(Long id, SaveNewASRequest
    // request) {
    // var accountShared = accountSharedRepository.findById(id).orElseThrow();
    // accountShared.setUsername(request.getUsername());
    // accountShared.setPassword(passwordEncoder.encode(request.getPassword()));
    // accountShared.setWebsiteUrl(request.getWebsiteUrl());
    // accountSharedRepository.save(accountShared);
    // return GeneralMessageResponse.builder().isSuccess(true).message("Account
    // Shared updated successfully").build();
    // }

    // public String ShareAccountShared(Long id, String email) {
    // var accountShared = accountSharedRepository.findById(id).orElseThrow();
    // var user = repository.findByEmail(email).orElseThrow();
    // accountShared.setUser(user);
    // accountSharedRepository.save(accountShared);
    // return "Account Shared shared successfully";
    // }

    // boolean checkIfAccountSharedExists(Integer id) {
    // return accountSharedRepository.existsById(id);
    // }

}
