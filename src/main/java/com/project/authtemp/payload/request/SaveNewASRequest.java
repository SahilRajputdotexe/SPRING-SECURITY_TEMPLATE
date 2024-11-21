package com.project.authtemp.payload.request;

import org.springframework.security.access.method.P;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveNewASRequest {
    private String username;
    private String password;
    private String websiteUrl;
    private String email;

}
