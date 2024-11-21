package com.project.authtemp.model.role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenRole {

    ACCESS("access"),
    REFRESH("refresh"),
    FORGOTPASSWORD("forgotpassword"),
    CREDENTIALS("credentials");

    private final String tokenRole;

    public String getTokenRole() {
        return tokenRole;
    }

}
