package com.project.authtemp.model.role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenRole {

    ACCESS("access"),
    REFRESH("refresh"),
    FORGOTPASSWORD("forgotpassword");

    private final String tokenRole;

    public String getTokenRole() {
        return tokenRole;
    }

}
