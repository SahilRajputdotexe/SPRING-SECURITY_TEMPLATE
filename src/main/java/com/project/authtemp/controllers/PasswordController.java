package com.project.authtemp.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/v1/password")
public class PasswordController {

    @PostMapping("/forgotpassword")
    public String forgotPassword() {
        return "POST:: forgot password";
    }

    @PostMapping("/resetpasswordWithPassword")
    public String resetPasswordWithPassword() {
        return "POST:: reset password";
    }

    @PostMapping("/resetpasswordWithToken")
    public String resetPasswordWithToken() {
        return "POST:: reset password with token";
    }

}
