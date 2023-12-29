package com.project.authtemp.payload.request;

import lombok.Builder;

import lombok.Getter;

import lombok.Setter;

@Builder
@Getter
@Setter
public class ForgotPasswordRequest {
    private String email;
}
