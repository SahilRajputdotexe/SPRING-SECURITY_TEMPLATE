package com.project.authtemp.payload.request;

import lombok.Builder;

import lombok.Getter;

import lombok.Setter;

@Builder
@Getter
@Setter
public class ChangePasswrodRequest {

    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
